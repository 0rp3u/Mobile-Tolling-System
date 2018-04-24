package pt.isel.ps.g30.tollingsystem.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter


abstract class BaseFragment<P: BasePresenter<V>, in V> : BaseView, Fragment() {

    abstract fun injectDependencies()

    protected lateinit var presenter: P

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        presenter = instantiatePresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttached(this as V)
    }

    override fun onStop() {
        presenter.onViewDetached()
        super.onStop()
    }
}
