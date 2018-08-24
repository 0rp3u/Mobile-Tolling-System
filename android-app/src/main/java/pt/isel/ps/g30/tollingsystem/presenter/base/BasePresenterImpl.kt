package pt.isel.ps.g30.tollingsystem.presenter.base

import pt.isel.ps.g30.tollingsystem.view.base.BaseView


abstract class BasePresenterImpl<V: BaseView> : BasePresenter<V> {

    protected var view: V? = null

    override fun onViewAttached(view: V) {
        this.view = view
    }

    override fun onViewDetached() {
        this.cancelRequest()
    }
}
