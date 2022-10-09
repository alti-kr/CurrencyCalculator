package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data

import com_sergii_tymofieiev_currency_calculator.base.ui.common.list_items.ItemType

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
data class CurrencyListItemModel(val id: Int, val name: String, val code: String, val itemType: ItemType, val rate: Int = 0) {
}