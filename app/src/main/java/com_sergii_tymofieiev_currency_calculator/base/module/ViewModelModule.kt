package com_sergii_tymofieiev_currency_calculator.base.module

import android.app.Application
import com_sergii_tymofieiev_currency_calculator.base.data.pref.PreferenceStorage
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchCurrencyListUseCase
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchRateByDateUseCase
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.view_model.MainFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
val viewModelModule = module {
    fun provideMainFragmentViewModel(
        context: Application,
        fetchCurrencyListUseCase: FetchCurrencyListUseCase,
        fetchRateByDateUseCase: FetchRateByDateUseCase,
        preferenceStorage: PreferenceStorage
    ): MainFragmentViewModel {
        return MainFragmentViewModel(context,fetchCurrencyListUseCase,fetchRateByDateUseCase,preferenceStorage)
    }
    single { provideMainFragmentViewModel(androidApplication(), get(), get(), get()) }

}