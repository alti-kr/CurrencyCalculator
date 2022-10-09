package com_sergii_tymofieiev_currency_calculator.base.data.repository.currency

import com_sergii_tymofieiev_currency_calculator.base.data.api.ApiService
import com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse
import com_sergii_tymofieiev_currency_calculator.base.data.mapper.CurrencyListMapper
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
class CurrencyRepositoryImpl(
    private val service: ApiService,
    private val currencyListMapper: CurrencyListMapper,
):CurrencyRepository {
    override suspend fun fetchCurrencyList(): NetworkResponse<List<CurrencyListItemModel>> {
        return when (val response = service.fetchCurrentCurrencyRate()) {
            is NetworkResponse.Error -> response
            is NetworkResponse.Success -> NetworkResponse.Success(
                currencyListMapper.map(response.content)
            )
        }
    }

    override suspend fun fetchCurrencyListByDate(parameter: String): NetworkResponse<List<CurrencyListItemModel>> {
        return when(val response = service.fetchRateByDate(parameter)){
            is NetworkResponse.Error -> response
            is NetworkResponse.Success -> NetworkResponse.Success(
                currencyListMapper.map(response.content)
            )
        }
    }
}