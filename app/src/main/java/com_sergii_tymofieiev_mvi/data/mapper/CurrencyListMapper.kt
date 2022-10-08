package com_sergii_tymofieiev_mvi.data.mapper

import com_sergii_tymofieiev_mvi.data.model.currency.CurrentCurrencyRateResponse
import com_sergii_tymofieiev_mvi.ui.main.main_fragment.data.CurrencyListItemModel

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
class CurrencyListMapper:Mapper<List<CurrentCurrencyRateResponse>, List<CurrencyListItemModel>> {
    override fun map(input: List<CurrentCurrencyRateResponse>): List<CurrencyListItemModel> {
        return input.map {
            CurrencyListItemModel(it.id, it.name, it.currencyCode)
        }
    }
}