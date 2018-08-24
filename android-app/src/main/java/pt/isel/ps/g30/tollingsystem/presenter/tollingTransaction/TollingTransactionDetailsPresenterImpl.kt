package pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction


import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.tollingTransaction.TollingTransactionDetailsView

class TollingTransactionDetailsPresenterImpl(private val interactor: TollingTransactionInteractor) :
        BasePresenterImpl<TollingTransactionDetailsView>(), TollingTransactionDetailsPresenter{

    companion object {
        private val TAG = this::class.java.simpleName
    }
    private val jobs = Job()

    override fun getTollingTransaction(TransactionId: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val Transaction = interactor.getTollingTransaction(TransactionId).await()
                view?.showTransaction(Transaction)
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