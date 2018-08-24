package pt.isel.ps.g30.tollingsystem.view.tollingTransaction

import android.view.View
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface TollingTransactionDetailsView : BaseView {

    fun showTransaction(tollingTransaction: TollingTransaction)
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}