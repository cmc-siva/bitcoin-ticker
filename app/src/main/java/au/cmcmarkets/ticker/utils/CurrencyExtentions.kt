package au.cmcmarkets.ticker.utils

import java.text.DecimalFormat
import java.text.NumberFormat


/**
 * Rounding of a double to the given decimals.
 *
 * @param decimals
 * @return
 */
fun Double?.format(decimals: Int? = 2): String {
    this?.let {
        return "%.${decimals}f".format(this)
    }

    return "-"
}

/**
 * Formats the prices to the given format.
 *
 * @param format
 * @return
 */

fun Double?.formatCurrency(format: String = "#,###.##"): String {
    this?.let {
        val formatter: NumberFormat = DecimalFormat(format)
        return formatter.format(it)
    }

    return "-"
}

/**
 * Gets the currency value from the string based on the given format
 *
 * @param format
 * @return
 */

fun String?.getCurrencyValue(format: String = "#,###.##"): Double? {
    this?.let {
        val formatter: NumberFormat = DecimalFormat(format)
        return formatter.parse(it).toDouble()
    }

    return null
}