package com_sergii_tymofieiev_mvi.ui.common.country_picker

import android.os.Parcelable
import com_sergii_tymofieiev_mvi.ui.common.list_items.BaseItemModel
import com_sergii_tymofieiev_mvi.ui.common.list_items.ItemType
import kotlinx.android.parcel.Parcelize


/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
@Parcelize
data class Country(
    val type: ItemType,
    val iso2: String,
    val name: String,
    val code: Int,
): BaseItemModel(type), Parcelable {
    fun getCodeWithPlus():String{
        return "+$code"
    }
}