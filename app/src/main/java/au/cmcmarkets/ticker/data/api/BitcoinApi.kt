package au.cmcmarkets.ticker.data.api

import au.cmcmarkets.ticker.data.model.TickerCurrency
import io.reactivex.Single

interface BitcoinApi {

    fun getTickerDetails(): Single<Map<String, TickerCurrency>>

}