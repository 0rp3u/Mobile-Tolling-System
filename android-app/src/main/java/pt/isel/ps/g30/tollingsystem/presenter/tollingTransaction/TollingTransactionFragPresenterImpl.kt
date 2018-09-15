package pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.tollingTransaction.TollingTransactionsFragmentView

class TollingTransactionFragPresenterImpl(
        authInteractor: AuthInteractor,
        private val interactor: TollingTransactionInteractor
) : BasePresenterImpl<TollingTransactionsFragmentView>(authInteractor), TollingTransactionFragPresenter{

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private var tollingTransactionList = listOf<TollingTransaction>()
    private val jobs = Job()

    override fun getTollingTransactionList(vehicleId: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                delay(1000)
                tollingTransactionList = interactor.getVehicleTransactionList(vehicleId).await()
                view?.showTransactionList(tollingTransactionList)
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