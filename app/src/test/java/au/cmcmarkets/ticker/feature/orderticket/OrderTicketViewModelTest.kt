package au.cmcmarkets.ticker.feature.orderticket

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import au.cmcmarkets.ticker.data.model.TickerCurrency
import au.cmcmarkets.ticker.data.repository.TickerRepository
import au.cmcmarkets.ticker.feature.orderticket.OrderTicketContract.ViewState
import au.cmcmarkets.ticker.utils.Helper.lambdaMock
import au.cmcmarkets.ticker.utils.TestSchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

/**
 * Created by Sivaraj on 25/6/20.
 */
class OrderTicketViewModelTest {

    @Captor
    lateinit var viewStateCaptor: ArgumentCaptor<ViewState>

    @Mock
    lateinit var mockTickerRepository: TickerRepository

    lateinit var viewModel: OrderTicketViewModel

    private val testObserver = lambdaMock<Observer<ViewState>>()

    private var testScheduler: TestScheduler = TestScheduler()

    private val testSchedulersProvider = object : TestSchedulersProvider() {
        override fun computation(): Scheduler {
            return testScheduler
        }
    }

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = OrderTicketViewModel(mockTickerRepository, testSchedulersProvider)
        viewModel.viewState.observeForever(testObserver)
    }

    @Test
    fun `when ticker update is available verify view state is updated`() {
        //Given
        val sampleSingle = Single.just(getSampleTickerCurrency())
        `when`(mockTickerRepository.getTickerDetails(GBP_TICKER)).thenReturn(sampleSingle)

        //When
        viewModel.startTickerUpdates()
        testScheduler.triggerActions()

        //Then
        viewStateCaptor.run {
            verify(testObserver).onChanged(capture())
            val tickerResultViewStates = allValues
            val viewStateOne = tickerResultViewStates[0] as ViewState
            assertFalse(viewStateOne.isLoading)
            assertEquals(viewStateOne.data, getSampleTickerCurrency())
        }
    }

    @Test
    fun `when ticker update is not available verify view state is not updated`() {
        //Given
        val errorSingle: Single<TickerCurrency> = Single.error(Exception("Null Pointer"))
        `when`(mockTickerRepository.getTickerDetails(GBP_TICKER)).thenReturn(errorSingle)

        //When
        viewModel.startTickerUpdates()
        testScheduler.triggerActions()

        //Then
        viewStateCaptor.run {
            verify(testObserver, never()).onChanged(capture())
        }
    }

    @Test
    fun `when ticker is updated multiple times verify view state is also updated the same no of times`() {
        //Given
        val numberOfUpdates = 3
        val expectedUpdates = numberOfUpdates + 1 // Initial run
        val sampleSingle = Single.just(getSampleTickerCurrency())
        `when`(mockTickerRepository.getTickerDetails(GBP_TICKER)).thenReturn(sampleSingle)

        //When
        viewModel.startTickerUpdates()

        repeat(numberOfUpdates) {
            testScheduler.triggerActions()
            testScheduler.advanceTimeBy(POLL_PERIOD_IN_SECS, TimeUnit.SECONDS)
        }

        //Then
        viewStateCaptor.run {
            verify(testObserver, times(expectedUpdates)).onChanged(capture())
            val tickerResultViewStates = allValues
            assertEquals("No of view updates", expectedUpdates, tickerResultViewStates.size)
        }
    }

    @Test
    fun `when unit is entered verify amount is updated`() {

        //given
        //Given
        val sampleSingle = Single.just(getSampleTickerCurrency())
        `when`(mockTickerRepository.getTickerDetails(GBP_TICKER)).thenReturn(sampleSingle)

        //When
        viewModel.startTickerUpdates()
        testScheduler.triggerActions()
        viewModel.onUnitChanged("10")

        //then
        assertEquals("Calculated amount matches", 1000.0, viewModel.liveDataHolder.editAmount.value)
        assertEquals(
            "Calculated amount format matches",
            "1,000.00",
            viewModel.liveDataHolder.getFormattedAmount()
        )

    }

    @Test
    fun `when unit is cleared verify live data is also cleared`() {

        //given
        //Given
        val sampleSingle = Single.just(getSampleTickerCurrency())
        `when`(mockTickerRepository.getTickerDetails(GBP_TICKER)).thenReturn(sampleSingle)

        //When
        viewModel.startTickerUpdates()
        testScheduler.triggerActions()
        viewModel.onUnitChanged("")

        //then
        assertEquals("Unit cleared", null, viewModel.liveDataHolder.editUnits.value)
        assertEquals(
            "Cleared unit format matches",
            "",
            viewModel.liveDataHolder.getFormattedUnits()
        )

    }

    @Test
    fun `when amount is entered verify unit is updated`() {

        //given
        //Given
        val sampleSingle = Single.just(getSampleTickerCurrency())
        `when`(mockTickerRepository.getTickerDetails(GBP_TICKER)).thenReturn(sampleSingle)

        //When
        viewModel.startTickerUpdates()
        testScheduler.triggerActions()
        viewModel.onAmountChanged("100")

        //then
        assertEquals("Calculated unit matches", 1.0, viewModel.liveDataHolder.editUnits.value)
        assertEquals(
            "Calculated unit format matches",
            "1.00",
            viewModel.liveDataHolder.getFormattedUnits()
        )

    }

    private fun getSampleViewState(): ViewState {
        return ViewState(false, getSampleTickerCurrency())
    }

    private fun getSampleTickerCurrency(): TickerCurrency {
        return TickerCurrency(
            "$",
            100.00,
            100.00,
            100.00,
            100.00
        )
    }

}