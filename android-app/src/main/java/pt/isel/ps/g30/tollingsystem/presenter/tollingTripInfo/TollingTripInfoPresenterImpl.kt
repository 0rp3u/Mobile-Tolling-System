package pt.isel.ps.g30.tollingsystem.presenter.tollingTripInfo

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.tollingTripInfo.TollingTripInfoView


class TollingTripInfoPresenterImpl(private val interactor: TollingTripInteractor) :
        BasePresenterImpl<TollingTripInfoView>(),TollingTripInfoPresenter {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private val jobs = Job()


    override fun getTripInfo(id: Int) {
        launch(UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                delay(1000)
                val tollingTripInfo = interactor.getTollingTrip(id).await()
                view?.showTripInfo(tollingTripInfo)
                view?.hideLoadingIndicator()
                view?.showDoneMessage()

            } catch (e: Throwable) {
                Log.d(TAG, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage()

            }
        }
    }




    override fun cancelRequest() {
        jobs.cancelChildren()
    }

}