package au.cmcmarkets.ticker.feature.orderticket

import androidx.lifecycle.*
import au.cmcmarkets.ticker.data.repository.TickerRepository
import au.cmcmarkets.ticker.feature.orderticket.OrderTicketContract.Actions
import au.cmcmarkets.ticker.feature.orderticket.OrderTicketContract.ViewState
import au.cmcmarkets.ticker.utils.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//TODO Change this to 15 after testing
const val POLL_PERIOD_IN_SECS = 5L
const val GBP_TICKER = "GBP"

class OrderTicketViewModel @Inject constructor(
    private val tickerRepository: TickerRepository,
    private val schedulers: SchedulersProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    private val mutableViewState = MutableLiveData<ViewState>()

    private val mutableAction = MutableLiveData<Actions>()

    val viewState: LiveData<ViewState> = mutableViewState

    val actions: LiveData<Actions> = mutableAction

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startTickerUpdates() {

        compositeDisposable.add(
            Observable.interval(
                0,
                POLL_PERIOD_IN_SECS,
                TimeUnit.SECONDS,
                schedulers.computation()
            )
                .flatMapSingle { tickerRepository.getTickerDetails(GBP_TICKER) }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainUiThread())
                .subscribe(
                    {
                        mutableViewState.value = ViewState(
                            false,
                            data = it
                        )
                    },
                    { error ->
                        // Handle error based on type
                    })
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopUpdates() {
        compositeDisposable.clear()
    }

}