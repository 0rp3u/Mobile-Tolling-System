package pt.isel.ps.g30.tollingsystem.presenter.navigation

import android.util.Log
import androidx.lifecycle.Observer
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.interactor.geofencing.GeofencingInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationFragmentView

class NavigationFragPresenterImpl(
        private val tollingInteractor: TollingTransactionInteractor,
        private val plazaInteractor: TollingPlazaInteractor,
        private val vehiclesInteractor: VehicleInteractor,
        private val geofencingInteractor: GeofencingInteractor
) : BasePresenterImpl<NavigationFragmentView>(), NavigationFragPresenter{

    companion object {
        private val TAG = NavigationFragPresenterImpl::class.java.simpleName
    }

    private val jobs = Job()



    override fun setUpMap(){
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val plazas = plazaInteractor.getActiveTollPlazaList().await()
                view?.showPlazaLocations(plazas)

                val activeVehicle = vehiclesInteractor.getActiveVehicle().await()

                view?.showActiveVehicle(activeVehicle)


                tollingInteractor.getCurrentTransactionLiveData().await().observe(view!!, Observer {
                    it?.let {
                        if(it.vehicle != null)
                            if(it.origin !=null) view?.showActiveTrip(it) else view?.removeActiveTrip(it)
                    }
                })


                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}", { setUpMap() })
            }
        }
    }




    override fun removeActiveVehicle(vehicle: Vehicle) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {

                geofencingInteractor.removeRegisteredGeofences().join()

                vehiclesInteractor.removeActiveVehicle(vehicle).await()

                view?.removeActiveVehicle()

                view?.hideLoadingIndicator()
                view?.showDoneMessage()

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") { removeActiveVehicle(vehicle) }
            }
        }
    }

    override fun setActiveVehicle(vehicle: Vehicle) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                geofencingInteractor.registerGeofences().join()

                vehiclesInteractor.setActiveVehicle(vehicle).join()

                view?.showActiveVehicle(vehicle)

                view?.hideLoadingIndicator()
                view?.showDoneMessage()

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") { setActiveVehicle(vehicle) }
            }
        }
    }

    override fun prepareVehiclesDialog() {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val vehicleList = vehiclesInteractor.getVehicleList().await()
                view?.showVehiclesDialog(vehicleList)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}", { prepareVehiclesDialog() })
            }

        }
    }

    override fun prepareCancelActiveTripDialog(currentTransaction: CurrentTransaction) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                view?.showCancelActiveTripDialog(currentTransaction)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}", { prepareCancelActiveTripDialog(currentTransaction) })
            }

        }
    }


    override fun startTrip(tollingPlaza: TollingPlaza) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val trip = tollingInteractor.startTollingTransaction(tollingPlaza).await()

                Log.d(TAG, "started transaction ${trip.vehicle} : ${trip.origin} -> ${trip.destination}")

                view?.showActiveTrip(trip)

                view?.hideLoadingIndicator()
                view?.showDoneMessage("passed on ${tollingPlaza.name}")

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") {startTrip(tollingPlaza)}
            }
        }
    }


    override fun finishTrip(tollingPlaza: TollingPlaza) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val trip = tollingInteractor.finishTransaction(tollingPlaza).await()

                Log.d(TAG, "finish transaction ${trip.vehicle} : ${trip.origin} -> ${trip.destination}")
                //view?.removeActiveTrip(transaction)

                view?.hideLoadingIndicator()
                view?.showDoneMessage("passed on ${tollingPlaza.name}")

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") {startTrip(tollingPlaza)}
            }
        }
    }

    override fun cancelActiveTrip(trip: CurrentTransaction) {
        Log.d(TAG, "canceling transaction ${trip.vehicle} : ${trip.origin} -> ${trip.destination}")
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {

                tollingInteractor.cancelCurrentTransaction(trip).await()


                //view?.removeActiveTrip(transaction)

                view?.hideLoadingIndicator()
                view?.showDoneMessage()

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}", {cancelActiveTrip(trip)})
            }
        }
    }

    override fun cancelRequest(){
        Log.d(TAG, "cancel all")
        jobs.cancelChildren() //propagates to children

    }
}