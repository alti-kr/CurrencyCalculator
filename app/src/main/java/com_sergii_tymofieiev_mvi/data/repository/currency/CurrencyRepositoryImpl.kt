package com_sergii_tymofieiev_mvi.data.repository.currency

import com_sergii_tymofieiev_mvi.data.api.ApiService
import com_sergii_tymofieiev_mvi.data.api.NetworkResponse
import com_sergii_tymofieiev_mvi.data.mapper.CurrencyListMapper
import com_sergii_tymofieiev_mvi.ui.main.main_fragment.data.CurrencyListItemModel

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
}