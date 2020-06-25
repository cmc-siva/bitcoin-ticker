package au.cmcmarkets.ticker.data.api

import au.cmcmarkets.ticker.data.model.TickerCurrency
import io.reactivex.Single
import retrofit2.http.GET

interface BitcoinApi {

    @GET("ticker")
    fun getTickerDetails(): Single<Map<String, TickerCurrency>>

}