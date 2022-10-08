package com_sergii_tymofieiev_mvi.di.module

import com_sergii_tymofieiev_mvi.data.mapper.CurrencyListMapper
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