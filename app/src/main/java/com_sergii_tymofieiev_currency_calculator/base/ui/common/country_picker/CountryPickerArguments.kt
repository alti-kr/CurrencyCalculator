package com_sergii_tymofieiev_currency_calculator.base.ui.common.country_picker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
@Parcelize
data class CountryPickerArguments(
    val itemLayout: Int,
    val isSearchEnabled: Boolean,
    val excludedCountries: List<String>,
    val admittedCountries: List<String>,
    val admittedToHeader: List<String>,
) : Parcelable