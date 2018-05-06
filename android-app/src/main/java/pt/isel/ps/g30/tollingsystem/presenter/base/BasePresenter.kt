package pt.isel.ps.g30.tollingsystem.presenter.base

interface BasePresenter<in V> {

    /**
     * Called when the view is attached to the presenter.
     *
     * @param view the view.
     */
    fun onViewAttached(view: V)

    /**
     * Called when the view is detached from the presenter.
     */
    fun onViewDetached()

    /**
     * Called when view is not interested on the data anymore, example is onDestroy
     */
    fun cancelRequest()
}
