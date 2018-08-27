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
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor
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
                            if(it.origin !=null) view?.showActiveTransaction(it) else view?.removeActiveTransaction(it)
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

    override fun prepareCancelActiveTransactionDialog(currentTransaction: CurrentTransaction) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                view?.showCancelActiveTransactionDialog(currentTransaction)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}", { prepareCancelActiveTransactionDialog(currentTransaction) })
            }

        }
    }


    override fun startTransaction(tollingPlaza: TollingPlaza) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val Transaction = tollingInteractor.startTollingTransaction(tollingPlaza).await()

                Log.d(TAG, "started transaction ${Transaction.vehicle} : ${Transaction.origin} -> ${Transaction.destination}")

                view?.showActiveTransaction(Transaction)

                view?.hideLoadingIndicator()
                view?.showDoneMessage("passed on ${tollingPlaza.name}")

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") {startTransaction(tollingPlaza)}
            }
        }
    }


    override fun finishTransaction(tollingPlaza: TollingPlaza) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val Transaction = tollingInteractor.finishTransaction(tollingPlaza).await()

                Log.d(TAG, "finish transaction ${Transaction.vehicle} : ${Transaction.origin} -> ${Transaction.destination}")
                //view?.removeActiveTransaction(transaction)

                view?.hideLoadingIndicator()
                view?.showDoneMessage("passed on ${tollingPlaza.name}")

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") {startTransaction(tollingPlaza)}
            }
        }
    }

    override fun cancelActiveTransaction(Transaction: CurrentTransaction) {
        Log.d(TAG, "canceling transaction ${Transaction.vehicle} : ${Transaction.origin} -> ${Transaction.destination}")
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {

                tollingInteractor.cancelCurrentTransaction(Transaction).await()


                //view?.removeActiveTransaction(transaction)

                view?.hideLoadingIndicator()
                view?.showDoneMessage()

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}", {cancelActiveTransaction(Transaction)})
            }
        }
    }

    override fun cancelRequest(){
        Log.d(TAG, "cancel all")
        jobs.cancelChildren() //propagates to children

    }
}