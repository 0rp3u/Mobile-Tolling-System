package pt.isel.ps.g30.tollingsystem.presenter.tollingtrip


import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripDetailsView

class TollingTripDetailsPresenterImpl(private val interactor: TollingTransactionInteractor) :
        BasePresenterImpl<TollingTripDetailsView>(), TollingTripDetailsPresenter{

    companion object {
        private val TAG = this::class.java.simpleName
    }
    private val jobs = Job()

    override fun getTollingTrip(tripId: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val trip = interactor.getTollingTransaction(tripId).await()
                view?.showTrip(trip)
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