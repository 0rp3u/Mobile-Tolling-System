package pt.isel.ps.g30.tollingsystem.view.tollingTransaction


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.fragment_vehicle_transaction.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extension.app
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.extension.longSnackbar
import pt.isel.ps.g30.tollingsystem.extension.snackbar
import pt.isel.ps.g30.tollingsystem.extension.startActivity
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction.TollingTransactionFragPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivity
import javax.inject.Inject

class TollingTransactionsFragment: BaseFragment<TollingTransactionFragPresenter, TollingTransactionsFragmentView>(), TollingTransactionsFragmentView{

    companion object {
        private val TAG = TollingTransactionsFragment::class.java.simpleName
    }

    lateinit var tollingTransactionsRecyclerViewAdapter: TollingTransactionsRecyclerViewAdapter

    @Inject
    override lateinit var presenter: TollingTransactionFragPresenter

    override fun injectDependencies() {
       app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "VIEW ON CREATE")
        val view = inflater.inflate(R.layout.fragment_vehicle_transaction, container, false)
        tollingTransactionsRecyclerViewAdapter = TollingTransactionsRecyclerViewAdapter {
            startActivity<TollingTransactionDetails>(
                    TollingTransactionDetails.EXTRA_Transaction_ID to it.id
            )
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            Log.d(TAG, "VIEW CREATED")
            recycler_view.apply{

                adapter = tollingTransactionsRecyclerViewAdapter
                layoutManager = LinearLayoutManager(activity)
            }

                presenter.getTollingTransactionList(arguments?.getInt(VehicleActivity.EXTRA_VEHICLE_ID)?:1)
    }

    override fun showTransactionList(list: List<TollingTransaction>) {
        Log.d(TAG, "show VEHICLE")
        tollingTransactionsRecyclerViewAdapter.historyList = list
    }


    override fun showLoadingIndicator() {

        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }


    override fun showDoneMessage(message:String?) {
        snackbar(this.view!!, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if(action != null)
            longSnackbar(this.view!!, error ?: "error, something when wrong","repeat?", action)
        else
            snackbar(this.view!!, error ?: "error, something when wrong")
    }

}
