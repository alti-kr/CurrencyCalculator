package com_sergii_tymofieiev_currency_calculator.base.data.model.currency

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
@Parcelize
data class CurrentCurrencyRateResponse(
    @field:SerializedName("r030")
    val id: Int,
    @field:SerializedName("txt")
    val name: String,
    @field:SerializedName("rate")
    val rate: String,
    @field:SerializedName("cc")
    val currencyCode: String,
): Parcelable