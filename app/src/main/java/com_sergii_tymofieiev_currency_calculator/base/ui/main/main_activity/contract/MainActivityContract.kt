package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_activity.contract

import com_sergii_tymofieiev_currency_calculator.base.mvi.effect.UiEffect
import com_sergii_tymofieiev_currency_calculator.base.mvi.event.UiEvent
import com_sergii_tymofieiev_currency_calculator.base.mvi.state.UiState

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
sealed class MainActivityEvent : UiEvent {
}

sealed class MainActivityState : UiState {
    object Idle : MainActivityState()
}

sealed class MainActivityEffect : UiEffect {

}