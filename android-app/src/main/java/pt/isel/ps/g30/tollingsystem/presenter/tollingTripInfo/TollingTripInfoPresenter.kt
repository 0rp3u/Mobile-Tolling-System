package pt.isel.ps.g30.tollingsystem.presenter.tollingTripInfo

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.tollingTripInfo.TollingTripInfoView

interface TollingTripInfoPresenter : BasePresenter<TollingTripInfoView> {

    fun getTripInfo(id: Int)

}