package pt.isel.ps.g30.tollingsystem
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclesFragPresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.TollingTripsFragmentView

class VehiclesFragPresenterTest : BasePresenterTest() {

    @Mock
    lateinit var view: TollingTripsFragmentView

    @InjectMocks
    lateinit var presenter: VehiclesFragPresenterImpl


    @Before
    fun attacheView(){
        presenter.onViewAttached(view)
    }


    @Test
    fun testGetVehicleList(){

        presenter.getVehicleList()


        verify(view, timeout(10000)).showLoadingIndicator()

        verify(view, timeout(10000)).hideLoadingIndicator()
    }

    @After
    fun testOnPresenterDestroyed() {
        presenter.onViewDetached()

        verify(presenter, times(1)).onViewAttached(view)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view)
    }

}
