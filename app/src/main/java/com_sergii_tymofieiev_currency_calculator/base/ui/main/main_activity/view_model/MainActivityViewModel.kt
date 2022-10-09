package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_activity.view_model

import android.app.Application
import com_sergii_tymofieiev_currency_calculator.base.mvi.view_model.BaseViewModel
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_activity.contract.MainActivityEffect
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_activity.contract.MainActivityEvent
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_activity.contract.MainActivityState

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class MainActivityViewModel(
    val context: Application,
): BaseViewModel<MainActivityEvent, MainActivityState, MainActivityEffect>(context) {
    override fun initialState() : MainActivityState {
        return MainActivityState.Idle
    }

    override fun handleEvent(event: MainActivityEvent) {
    }
}