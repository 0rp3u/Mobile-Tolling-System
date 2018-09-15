package pt.isel.ps.g30.tollingsystem.presenter.base

import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.view.base.BaseView


abstract class BasePresenterImpl<V: BaseView>(private val authInteractor: AuthInteractor) : BasePresenter<V> {

    protected var view: V? = null

    override fun onViewAttached(view: V) {
        this.view = view
    }

    override fun onViewDetached() {
        this.cancelRequest()
    }

    override fun logout() {
        authInteractor.logout()
    }



}
