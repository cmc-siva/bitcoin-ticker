package au.cmcmarkets.ticker.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Sivaraj on 25/6/20.
 */

class TickerResponse : HashMap<String, TickerCurrency>()

data class TickerCurrency(
    @SerializedName(value = "symbol")
    val symbol: String,
    @SerializedName(value = "last")
    val recentPrice: Double,
    @SerializedName(value = "buy")
    val buyPrice: Double,
    @SerializedName(value = "sell")
    val sellPrice: Double,
    @SerializedName(value = "15m")
    val delayedPrice: Double
) {
    val spread: Double
        get() = sellPrice - buyPrice
}
