package com_sergii_tymofieiev_currency_calculator.base.util.text_watcher

import java.text.NumberFormat
import java.text.ParseException
import java.util.*

/**
 * Created by Sergii Tymofieiev on 09.10.2022
 */
internal fun parseMoneyValue(
    value: String,
    groupingSeparator: String,
    currencySymbol: String = ""
): String =
    value.replace(groupingSeparator, "").replace(currencySymbol, "")

internal fun parseMoneyValueWithLocale(
    locale: Locale,
    value: String,
    groupingSeparator: String,
    currencySymbol: String = ""
): Number {

    val valueWithoutSeparator = parseMoneyValue(value, groupingSeparator, currencySymbol)
    return try {
        NumberFormat.getInstance(locale).parse(valueWithoutSeparator)!!
    } catch (exception: ParseException) {
        0
    }
}

fun parseStringAmountToInt(amount: String?): Int? {
    return if (amount.isNullOrEmpty()) {
        null
    } else {
        try {
            (amount.replace(",", ".").toFloat() * 100).toInt()
        } catch (e: Exception) {
            null
        }
    }
}