package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.contract

import com.google.android.gms.maps.model.LatLng
import com_sergii_tymofieiev_currency_calculator.base.mvi.effect.UiEffect
import com_sergii_tymofieiev_currency_calculator.base.mvi.event.UiEvent
import com_sergii_tymofieiev_currency_calculator.base.mvi.state.UiState
import com_sergii_tymofieiev_currency_calculator.base.ui.data.PlaceData
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
sealed class MainFragmentEvent : UiEvent {
    data class CalculateAmount(val date: Long, val currencyFrom: CurrencyListItemModel, val currencyTo:CurrencyListItemModel, val amount: String) : MainFragmentEvent()
    object FetchHistory:MainFragmentEvent()
}

enum class MainFragmentStatus { IDLE, SUCCESS, FAILED }
enum class MainFragmentStateType{IDLE, CURRENCY_LIST, AMOUNT}
data class MainFragmentState(
    val status: MainFragmentStatus = MainFragmentStatus.IDLE,
    val type: MainFragmentStateType = MainFragmentStateType.IDLE,
    val currencyList: List<CurrencyListItemModel> = emptyList(),
    val dateOfRate: Long = 0,
    val rateList: List<CurrencyListItemModel> = emptyList(),
    val currencyAmount: Int = 0
) : UiState

sealed class MainFragmentEffect : UiEffect {
    data class ShowErrorEffect(val message: String) : MainFragmentEffect()
    data class ToggleProgress(val onOff: Boolean) : MainFragmentEffect()
    data class HistoryAsString(val history:String) : MainFragmentEffect()
}