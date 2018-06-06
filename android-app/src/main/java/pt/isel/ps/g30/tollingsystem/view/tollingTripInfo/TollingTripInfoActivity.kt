package pt.isel.ps.g30.tollingsystem.view.tollingTripInfo


import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_trip_info.*
import kotlinx.android.synthetic.main.activity_vehicle_registry.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip

import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.extensions.longSnackbar
import pt.isel.ps.g30.tollingsystem.extensions.snackbar
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.tollingTripInfo.TollingTripInfoPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity


import javax.inject.Inject

class TollingTripInfoActivity: BaseActivity<TollingTripInfoPresenter, TollingTripInfoView>(), TollingTripInfoView{

    companion object {
//        const val EXTRA_VEHICLE_ID = "EXTRA_VEHICLE_ID"
//        const val EXTRA_VEHICLE_LICENSE_PLATE = "EXTRA_VEHICLE_LICENSE_PLATE"
    }


    @Inject
    override lateinit var presenter: TollingTripInfoPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_info)
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        presenter.getTripInfo(1)
    }

    override fun onDestroy() {
        presenter.cancelRequest()
        super.onDestroy()
    }

    override fun showTripInfo(tripInfo: TollingTrip) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDoneMessage(message:String?) {
        snackbar(this.coordinator, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if(action != null)
            longSnackbar(this.coordinator, error ?: "error, something when wrong","repeat?", action)
        else
            snackbar(this.coordinator, error ?: "error, something when wrong")
    }
}
