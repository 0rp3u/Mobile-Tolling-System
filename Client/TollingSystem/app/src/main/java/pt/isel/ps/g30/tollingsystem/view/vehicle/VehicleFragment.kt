package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.VehicleModule
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenter
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenterImpl
import pt.isel.ps.g30.tollingsystem.databinding.VehiclesFragmentBinding
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import javax.inject.Inject

class VehicleFragment: BaseFragment<VehiclePresenter, VehicleView>(), VehicleView{

    companion object {
        val ARG_TYPE_ID = "ARG_TYPE_ID"
        val ARG_TYPE_NAME = "ARG_TYPE_NAME"
    }

    lateinit var vehicleRecyclerViewAdapter: VehicleRecyclerViewAdapter
    private lateinit var bind: VehiclesFragmentBinding

    @Inject
    lateinit var interactor: VehicleInteractor

    override fun injectDependencies() {
       app.applicationComponent
                .plus(VehicleModule())
                .injectTo(this)
    }

    override fun instantiatePresenter(): VehiclePresenter
            = VehiclePresenterImpl(interactor)





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.vehicles_fragment, container, false)

        bind = DataBindingUtil.bind<ViewDataBinding>(view) as VehiclesFragmentBinding

        vehicleRecyclerViewAdapter = VehicleRecyclerViewAdapter { (id, name) ->

            //TODO on vehicle click

        }

        bind.recyclerView.apply{
            adapter = vehicleRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)

        }
        presenter.getVehicleList()

        return view
    }

    override fun showVehicleList(list: List<Vehicle>) {
        vehicleRecyclerViewAdapter.vehicleList = list
    }


    override fun showLoadingIndicator() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showDoneMessage() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
