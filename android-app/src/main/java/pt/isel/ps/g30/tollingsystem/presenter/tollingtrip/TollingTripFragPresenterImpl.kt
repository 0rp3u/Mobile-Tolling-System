package pt.isel.ps.g30.tollingsystem.presenter.tollingtrip

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragmentView

class TollingTripFragPresenterImpl(private val interactor: TollingTripInteractor) :
        BasePresenterImpl<TollingTripsFragmentView>(), TollingTripFragPresenter{

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private var tollingTripList = listOf<TollingTrip>()
    private val jobs = Job()

    override fun getTollingTripList(vehicleId: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                delay(1000)
                tollingTripList = interactor.getVehicleTripList(vehicleId).await()
                view?.showTripList(tollingTripList)
                view?.hideLoadingIndicator()
                view?.showDoneMessage()

            }catch (e: Throwable){
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}