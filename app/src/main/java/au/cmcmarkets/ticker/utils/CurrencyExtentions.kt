package au.cmcmarkets.ticker.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.regex.Pattern


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

    return ""
}

/**
 * Formats the prices to the given format.
 *
 * @param format
 * @return
 */

fun Double?.formatCurrency(format: String = "#,##0.00"): String {
    this?.let {
        val formatter: NumberFormat = DecimalFormat(format)
        return formatter.format(it)
    }

    return ""
}

/**
 * Gets the currency value from the string based on the given format
 *
 * @param format
 * @return
 */

fun String?.getCurrencyValue(format: String = "#,##0.00"): Double? {
    this?.let {
        val formatter: NumberFormat = DecimalFormat(format)
        // Unsafe use, handle this better
        return formatter.parse(it).toDouble()
    }

    return null
}

// Quick hack to check for validity
//https://stackoverflow.com/questions/43084537/what-is-the-regex-for-decimal-numbers-in-java
fun String.findDecimalDigits() =
    Pattern.compile("^[0-9]+\\.?[0-9]*").matcher(this).run { if (find()) group() else "" }!!