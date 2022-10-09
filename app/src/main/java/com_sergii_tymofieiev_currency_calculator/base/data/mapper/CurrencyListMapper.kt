package com_sergii_tymofieiev_currency_calculator.base.data.mapper

import com_sergii_tymofieiev_currency_calculator.base.data.model.currency.CurrentCurrencyRateResponse
import com_sergii_tymofieiev_currency_calculator.base.ui.common.list_items.ItemType
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import com_sergii_tymofieiev_currency_calculator.base.util.text_watcher.parseStringAmountToInt

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
class CurrencyListMapper:Mapper<List<CurrentCurrencyRateResponse>, List<CurrencyListItemModel>> {
    override fun map(input: List<CurrentCurrencyRateResponse>): List<CurrencyListItemModel> {
        return input.map {
            CurrencyListItemModel(it.id, it.name, it.currencyCode, ItemType.ITEM, parseStringAmountToInt(it.rate)?:0)
        }
    }
}