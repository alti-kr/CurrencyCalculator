package com_sergii_tymofieiev_currency_calculator.base.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View?) {
    view?.let {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}