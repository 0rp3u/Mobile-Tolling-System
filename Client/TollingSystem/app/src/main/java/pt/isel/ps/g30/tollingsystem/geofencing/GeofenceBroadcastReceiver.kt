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
package pt.isel.ps.g30.tollingsystem.geofencing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
//
///**
// * Receiver for geofence transition changes.
// *
// *
// * Receives geofence transition events from Location Services in the form of an Intent containing
// * the transition type and geofence id(s) that triggered the transition. Creates a JobIntentService
// * that will handle the intent in the background.
// */
//class GeofenceBroadcastReceiver : BroadcastReceiver() {
//
//    /**
//     * Receives incoming intents.
//     *
//     * @param context the application context.
//     * @param intent  sent by Location Services. This Intent is provided to Location
//     * Services (inside a PendingIntent) when addGeofences() is called.
//     */
//    override fun onReceive(context: Context, intent: Intent) {
//        // Enqueues a JobIntentService passing the context and intent as parameters
//        GeofenceTransitionsJobIntentService.enqueueWork(context, intent)
//    }
//}
