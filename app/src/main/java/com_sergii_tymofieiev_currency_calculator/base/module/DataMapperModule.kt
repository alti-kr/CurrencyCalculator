package com_sergii_tymofieiev_currency_calculator.base.module


import com_sergii_tymofieiev_currency_calculator.base.data.mapper.CurrencyListMapper
import org.koin.dsl.module

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
val mapperModule = module {
    fun provideCurrencyListMapper(): CurrencyListMapper {
        return CurrencyListMapper()
    }
    single { provideCurrencyListMapper() }
}