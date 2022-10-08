package com_sergii_tymofieiev_mvi.data.repository.currency

import com_sergii_tymofieiev_mvi.data.api.NetworkResponse
import com_sergii_tymofieiev_mvi.ui.main.main_fragment.data.CurrencyListItemModel

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
interface CurrencyRepository {
    suspend fun fetchCurrencyList(): NetworkResponse<List<CurrencyListItemModel>>
}