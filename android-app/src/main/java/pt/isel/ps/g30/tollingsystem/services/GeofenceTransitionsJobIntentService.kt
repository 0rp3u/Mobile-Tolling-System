/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.isel.ps.g30.tollingsystem.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build

import android.text.TextUtils
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder

import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.database.model.ActiveTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.extension.getIconResource
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity

import java.util.ArrayList
import javax.inject.Inject

/**
 * Listener for geofence transition changes.
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition. Creates a notification
 * as the output.
 */
class GeofenceTransitionsJobIntentService : JobIntentService() {

    @Inject
    lateinit var tollingTripInteractor: TollingTripInteractor

    @Inject
    lateinit var tollingPlazaInteractor: TollingPlazaInteractor

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    fun injectDependencies() {
        (this.application as TollingSystemApp).applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     * Services (inside a PendingIntent) when addGeofences() is called.
     */
    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
//            val errorMessage = GeofenceErrorMessages.getErrorString(this,
//                    geofencingEvent.errorCode)

            //Log.e(TAG, errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            // Get the transition details as a String.
            val geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition,
                    triggeringGeofences)

            launch{
                val plaza = tollingPlazaInteractor.getTollPlaza(triggeringGeofences.first().requestId.toInt()).await()
                val currentTrip = tollingTripInteractor.getActiveTollingTrip().await()

                // Send notification and log the transition details.

                if(currentTrip.origin!=null){
                    val trip = tollingTripInteractor.finishTollingTrip(plaza).await().also { it.origin = currentTrip.origin } //<- hack, needed for now
                    sendNotification(trip)
                    Log.e(TAG, "finished trip @ ${trip.destination}")
                } else{
                    val trip = tollingTripInteractor.startTollingTrip(plaza).await()
                    sendNotification(trip)
                    Log.e(TAG, "started trip @ ${trip.origin}")
                }
            }
            Log.i(TAG, geofenceTransitionDetails)
        } else {
            // Log the error.
            //Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition))
        }
    }

    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geofenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as String.
     */
    private fun getGeofenceTransitionDetails(
            geofenceTransition: Int,
            triggeringGeofences: List<Geofence>): String {

        val geofenceTransitionString = getTransitionString(geofenceTransition)

        // Get the Ids of each geofence that was triggered.
        val triggeringGeofencesIdsList = ArrayList<String>()
        for (geofence in triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.requestId)
        }
        val triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList)

        return "$geofenceTransitionString: $triggeringGeofencesIdsString"
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private fun sendNotification(trip: ActiveTrip) {
        // Get an instance of the Notification manager
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            // Create the channel for the notification
            val mChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel)
        }

        // Create an explicit content Intent that starts the main Activity.
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)

        // Construct a task stack.
        val stackBuilder = TaskStackBuilder.create(this)

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity::class.java)

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent)

        // Get a PendingIntent containing the entire back stack.
        val notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        // Get a notification builder that's compatible with platform versions >= 4
        val builder = NotificationCompat.Builder(this)

        // Define the notification settings.
        val carIcon = trip.vehicle?.getIconResource() ?: R.drawable.ic_directions_car_black_24dp
        builder.setSmallIcon(carIcon)
                .setLargeIcon(BitmapFactory.decodeResource(resources, carIcon))
                .setColor(Color.RED)
                .setContentIntent(notificationPendingIntent)

        if(trip.destination!=null){
            builder.setContentTitle("finished trip")
                    .setContentText("finished trip from ${trip.origin?.name}  to ${trip.destination?.name}")
        } else{
            builder.setContentTitle("started trip")
                    .setContentText("started trip on ${trip.origin?.name}")
        }

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID) // Channel ID
        }

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true)

        // Issue the notification
        mNotificationManager.notify(0, builder.build())
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
    private fun getTransitionString(transitionType: Int): String {
        return "trigger"
//        when (transitionType) {
//            Geofence.GEOFENCE_TRANSITION_ENTER -> return getString(R.string.geofence_transition_entered)
//            Geofence.GEOFENCE_TRANSITION_EXIT -> return getString(R.string.geofence_transition_exited)
//            else -> return getString(R.string.unknown_geofence_transition)
//        }
    }

    companion object {

        private val JOB_ID = 573

        private val TAG = "GeofenceTransitionsIS"

        private val CHANNEL_ID = "channel_01"

        /**
         * Convenience method for enqueuing work in to this service.
         */
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, GeofenceTransitionsJobIntentService::class.java, JOB_ID, intent)
        }
    }
}