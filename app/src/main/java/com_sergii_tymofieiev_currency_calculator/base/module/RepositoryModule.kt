package com_sergii_tymofieiev_currency_calculator.base.module

import com_sergii_tymofieiev_currency_calculator.base.data.mapper.CurrencyListMapper
import com_sergii_tymofieiev_currency_calculator.base.data.repository.currency.CurrencyRepository
import com_sergii_tymofieiev_currency_calculator.base.data.repository.currency.CurrencyRepositoryImpl
import org.koin.dsl.module

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
val repositoryModule = module {
    fun provideCurrencyRepository(
        apiService: com_sergii_tymofieiev_currency_calculator.base.data.api.ApiService,
        currencyListMapper: CurrencyListMapper,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService,currencyListMapper)
    }
    single { provideCurrencyRepository(get(), get()) }
}
