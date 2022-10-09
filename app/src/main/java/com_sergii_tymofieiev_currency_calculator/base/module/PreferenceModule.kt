package com_sergii_tymofieiev_currency_calculator.base.module

import android.content.Context
import com_sergii_tymofieiev_currency_calculator.base.data.pref.PreferenceStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
val preferenceModule = module {
    fun providePreferenceStorage(context: Context): PreferenceStorage {
        return PreferenceStorage(context)
    }

    single { providePreferenceStorage(androidApplication()) }
}