package au.cmcmarkets.ticker.feature.orderticket

import au.cmcmarkets.ticker.data.model.TickerCurrency

/**
 * Created by Sivaraj on 25/6/20.
 */
interface OrderTicketContract {
    sealed class Actions {
        object Cancel : Actions()
        object Confirm : Actions()
    }

    data class ViewState(
        val isLoading: Boolean,
        val data: TickerCurrency
    )
}