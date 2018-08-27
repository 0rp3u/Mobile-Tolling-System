package pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.tollingTransaction.TollingTransactionDetailsView

interface TollingTransactionDetailsPresenter : BasePresenter<TollingTransactionDetailsView> {

    fun getTollingTransaction(TransactionId: Int)

}