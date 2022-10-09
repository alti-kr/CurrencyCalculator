package com_sergii_tymofieiev_currency_calculator.base.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
@Parcelize
data class ErrorResponseModel(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("request_id")
    val requestId: String?,
) : Parcelable