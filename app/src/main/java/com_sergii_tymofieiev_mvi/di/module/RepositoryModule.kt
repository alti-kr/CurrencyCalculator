package com_sergii_tymofieiev_mvi.di.module

import com_sergii_tymofieiev_mvi.data.api.ApiService
import com_sergii_tymofieiev_mvi.data.mapper.CurrencyListMapper
import com_sergii_tymofieiev_mvi.data.repository.currency.CurrencyRepository
import com_sergii_tymofieiev_mvi.data.repository.currency.CurrencyRepositoryImpl
import org.koin.dsl.module

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
val repositoryModule = module {
    fun provideCurrencyRepository(
        apiService: ApiService,
        currencyListMapper: CurrencyListMapper,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService,currencyListMapper)
    }
    single { provideCurrencyRepository(get(), get()) }
}
