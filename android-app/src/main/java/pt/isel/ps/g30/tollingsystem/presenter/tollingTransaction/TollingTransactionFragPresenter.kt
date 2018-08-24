package pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction

import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.tollingTransaction.TollingTransactionsFragmentView

interface TollingTransactionFragPresenter : BasePresenter<TollingTransactionsFragmentView> {

    fun getTollingTransactionList(vehicleId: Int)

}