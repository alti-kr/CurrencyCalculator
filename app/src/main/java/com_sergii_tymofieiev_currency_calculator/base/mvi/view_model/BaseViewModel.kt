package com_sergii_tymofieiev_currency_calculator.base.mvi.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com_sergii_tymofieiev_currency_calculator.base.mvi.effect.UiEffect
import com_sergii_tymofieiev_currency_calculator.base.mvi.event.UiEvent
import com_sergii_tymofieiev_currency_calculator.base.mvi.state.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect>(application: Application) :
    AndroidViewModel(application) {
    private val initialState: State by lazy {
        initialState()
    }

    abstract fun initialState(): State

    private val currentState: State get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}