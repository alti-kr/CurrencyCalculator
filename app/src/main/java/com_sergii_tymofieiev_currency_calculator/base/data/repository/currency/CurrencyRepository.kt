package com_sergii_tymofieiev_currency_calculator.base.data.repository.currency

import com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
interface CurrencyRepository {
    suspend fun fetchCurrencyList(): NetworkResponse<List<CurrencyListItemModel>>
    suspend fun fetchCurrencyListByDate(parameter: String): NetworkResponse<List<CurrencyListItemModel>>
}