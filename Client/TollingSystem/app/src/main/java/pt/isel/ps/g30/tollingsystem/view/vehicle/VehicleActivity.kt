package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.os.Bundle
import android.support.design.widget.Snackbar

import kotlinx.android.synthetic.main.activity_vehicle.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.VehicleModule
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenter
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import javax.inject.Inject

class VehicleActivity : BaseActivity<VehiclePresenter, VehicleView>(), VehicleView{


    @Inject
    lateinit var interactor: VehicleInteractor

    override fun injectDependencies() {
        app.applicationComponent
                .plus(VehicleModule())
                .injectTo(this)
    }

    override fun instantiatePresenter(): VehiclePresenter
            = VehiclePresenterImpl(interactor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.getVehicleList()
    }

    override fun onDestroy() {
        presenter.cancelRequest()
        super.onDestroy()
    }

    override fun showDoneMessage() {
        Snackbar.make(findViewById(R.id.main_layout), "done", Snackbar.LENGTH_SHORT)
                .show()
    }

    override fun showErrorMessage() {
        Snackbar.make(findViewById(R.id.main_layout), "error", Snackbar.LENGTH_SHORT)
                .setAction("retry") {
                    presenter
                }
                .show()
    }

    override fun showVehicleList(list: List<Vehicle>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
