package au.cmcmarkets.ticker.data.repository

import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.model.TickerCurrency
import io.reactivex.Single

/**
 * Created by Sivaraj on 25/6/20.
 */
//TODO Add an interface for the repo
class TickerRepository(
    private val bitcoinApi: BitcoinApi
) {

    fun getTickerDetails(tickerName: String): Single<TickerCurrency> {
        return bitcoinApi.getTickerDetails()
            .map {
                it[tickerName]
            }
    }

}