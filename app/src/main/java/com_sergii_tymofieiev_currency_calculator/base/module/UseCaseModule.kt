package com_sergii_tymofieiev_currency_calculator.base.module

import com_sergii_tymofieiev_currency_calculator.base.data.repository.currency.CurrencyRepository
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchCurrencyListUseCase
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchCurrencyListUseCaseImpl
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchRateByDateUseCase
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchRateByDateUseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
val useCaseModule = module {

    fun provideFetchCurrencyListUseCase(
        dispatcher: CoroutineDispatcher,
        currencyRepository: CurrencyRepository
    ): FetchCurrencyListUseCase {
        return FetchCurrencyListUseCaseImpl(dispatcher,currencyRepository)
    }
    single { provideFetchCurrencyListUseCase(Dispatchers.IO, get()) }

    fun provideFetchRateByDateUseCase(
        dispatcher: CoroutineDispatcher,
        currencyRepository: CurrencyRepository
    ): FetchRateByDateUseCase{
        return FetchRateByDateUseCaseImpl(dispatcher,currencyRepository)
    }
    single { provideFetchRateByDateUseCase(Dispatchers.IO, get()) }
}