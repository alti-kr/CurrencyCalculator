package com_sergii_tymofieiev_currency_calculator.base.data.pref

import android.content.Context
import android.content.SharedPreferences
import com_sergii_tymofieiev_currency_calculator.base.data.pref.pref_data.StringPreference

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class PreferenceStorage(
    context: Context
) {

    private val prefs: Lazy<SharedPreferences> = lazy { // Lazy to prevent IO access to main thread.
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )
    }
    var exchangeHistory by StringPreference(prefs, EXCHANGE_HISTORY, "")
    companion object {
        private const val PREFS_NAME = "mvi_preferences"
        private const val EXCHANGE_HISTORY = "exchange_history"
    }
}