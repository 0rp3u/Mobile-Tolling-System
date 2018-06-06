package pt.isel.ps.g30.tollingsystem.interactor.geofencing
import android.annotation.SuppressLint
import android.app.PendingIntent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.experimental.*
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase

class GeofencingInteractorImpl(
        private val geofenceBroadcastReeiver:PendingIntent,
        private val mGeofencingClient: GeofencingClient,
        private val tollingSystemDatabase: TollingSystemDatabase
): GeofencingInteractor {



    override suspend fun getActiveGeofences(): Deferred<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @SuppressLint("MissingPermission")
    override suspend fun registerGeofences() = launch {

        val activeTollplazas = tollingSystemDatabase.TollingDao().findAll()

        val geofences = activeTollplazas.map {
            Geofence.Builder()
                    .setRequestId( "${it.id}")
                    .setCircularRegion(
                            it.lat,
                            it.Lng,
                            50f //TODO
                    )
                    .setExpirationDuration(24* 60 * 60 * 1000) //TODO
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build() }

        val request = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofences)
                .build()

        suspendCancellableCoroutine { continuation ->
            mGeofencingClient.addGeofences(request, geofenceBroadcastReeiver)?.run {
                addOnSuccessListener { ret ->
                    launch {

                                tollingSystemDatabase.TollingDao().update(*(activeTollplazas.map { it.active = true; it }.toTypedArray()))
                        continuation.resume(Unit)
                    }
                }
                addOnFailureListener { continuation.resumeWithException(it) }
            }
        }
    }

    override suspend fun removeRegisteredGeofences() = launch {

        val activeTollplazas = tollingSystemDatabase.TollingDao().findActive()

        suspendCancellableCoroutine { continuation ->
            mGeofencingClient.removeGeofences(activeTollplazas.map { "${it.id}" })?.run {
                addOnSuccessListener { ret ->
                    launch{
                                tollingSystemDatabase.TollingDao().update(*(activeTollplazas.map { it.active = false; it }.toTypedArray()))
                        continuation.resume(Unit)
                    }
                }
                addOnFailureListener { continuation.resumeWithException(it) }
            }
        }
    }


}
