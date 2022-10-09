package com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse
import com_sergii_tymofieiev_currency_calculator.base.data.pref.PreferenceStorage
import com_sergii_tymofieiev_currency_calculator.base.mvi.view_model.BaseViewModel
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchCurrencyListUseCase
import com_sergii_tymofieiev_currency_calculator.base.data.usecase.FetchRateByDateUseCase
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.contract.*
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import com_sergii_tymofieiev_currency_calculator.base.util.text_watcher.parseStringAmountToInt
import kotlinx.coroutines.launch
import java.lang.Math.min
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class MainFragmentViewModel(
    val context: Application,
    private val fetchCurrencyListUseCase: FetchCurrencyListUseCase,
    private val fetchRateByDateUseCase: FetchRateByDateUseCase,
    private val preferenceStorage: PreferenceStorage,
): BaseViewModel<MainFragmentEvent, MainFragmentState, MainFragmentEffect>(context) {
    override fun initialState(): MainFragmentState {
        return MainFragmentState()
    }
    init {
        fetchCurrencyList()
    }

    override fun handleEvent(event: MainFragmentEvent) {
        when(event){
            is MainFragmentEvent.CalculateAmount -> calculateAmount(event)
            MainFragmentEvent.FetchHistory -> {
                setEffect { MainFragmentEffect.HistoryAsString(getHistoryAsString()) }
            }
        }

    }

    private fun calculateAmount(event: MainFragmentEvent.CalculateAmount) {
        if(!uiState.value.rateList.isEmpty() && uiState.value.dateOfRate == event.date ){
            calculate(uiState.value.rateList, event)
        }else {
            viewModelScope.launch {
                when (val result = fetchRateByDateUseCase(
                    SimpleDateFormat(
                        "yyyyMMdd",
                        Locale("us")
                    ).format(event.date)
                )) {
                    is NetworkResponse.Error -> {
                        setEffect { MainFragmentEffect.ShowErrorEffect(result.error.message ?: "") }
                    }
                    is NetworkResponse.Success -> {
                        calculate(result.content, event)
                    }
                }
            }
        }
    }

    private fun calculate(content: List<CurrencyListItemModel>, event: MainFragmentEvent.CalculateAmount) {
        if(event.currencyFrom.code == event.currencyTo.code){
            setState { copy(status = MainFragmentStatus.SUCCESS , type = MainFragmentStateType.AMOUNT,rateList = content, dateOfRate = event.date, currencyAmount = parseStringAmountToInt(event.amount)?:0 ) }
            updateHistory(event, parseStringAmountToInt(event.amount)?:0 )
        }else {
            val  amount = calculateByCrossRate(findCurrency(event.currencyFrom, content), findCurrency(event.currencyTo, content),parseStringAmountToInt(event.amount))
            setState { copy(status = MainFragmentStatus.SUCCESS , type = MainFragmentStateType.AMOUNT,rateList = content, dateOfRate = event.date, currencyAmount =  amount) }
            updateHistory(event, amount)
        }
        setEffect { MainFragmentEffect.HistoryAsString(getHistoryAsString()) }
    }

    private fun updateHistory(event: MainFragmentEvent.CalculateAmount, amount: Int) {
        val st = SimpleDateFormat("dd MMM yyyy", Locale("us")).format(event.date)+" :: "+ event.currencyFrom.code +" -> " + event.currencyTo.code+ " :: "+(amount.toFloat()/100).toString()
        val sv = listToStoredString(addToLis(st,fetchHistoryList()))
        preferenceStorage.exchangeHistory = sv
    }
    private val divider = ";;"
    private fun fetchHistoryList():List<String>{
        return preferenceStorage.exchangeHistory?.split(divider)?: emptyList()
    }
    private fun addToLis(value:String, list: List<String>): List<String>{
        val tList = ArrayList<String>()
        tList.add(value)
        for (ind in 0..min(list.size-1, 8) ){
            tList.add(list[ind])
        }
        return tList
    }
    private fun listToStoredString(list: List<String>): String{
        val sb = StringBuilder()
        list.forEach {
            sb.append(it)
            sb.append(divider)
        }
        return  sb.toString().substring(0,sb.toString().length-2)
    }
private fun getHistoryAsString():String{
    val sb  =  StringBuilder()
    fetchHistoryList().forEach {
        sb.append(it)
        sb.append("\n")
    }
    return sb.toString()
}
    private fun findCurrency(currency: CurrencyListItemModel, content: List<CurrencyListItemModel>): CurrencyListItemModel? {
        content.forEach {
            if(currency.code == it.code){
                return it
            }
        }
        return null
    }

    private fun calculateByCrossRate(
        findCurrencyFrom: CurrencyListItemModel?,
        findCurrencyTo: CurrencyListItemModel?,
        amountInt: Int?
    ): Int {
        return if(findCurrencyFrom == null || findCurrencyTo == null || amountInt == null){
            setEffect { MainFragmentEffect.ShowErrorEffect("Not found currency") }
            0
        }else{
            return (findCurrencyFrom.rate * amountInt)/ findCurrencyTo.rate
        }
    }



    private fun fetchCurrencyList(){
        viewModelScope.launch {
            when(val result = fetchCurrencyListUseCase(Unit)){
                is NetworkResponse.Error -> {
                    setEffect { MainFragmentEffect.ShowErrorEffect(result.error.message?:"") }
                }
                is NetworkResponse.Success ->{
                    Log.d("My_log", " Success = " + result.content.size)
                    setState { copy(status = MainFragmentStatus.SUCCESS, type = MainFragmentStateType.CURRENCY_LIST, currencyList = result.content ) }

                }
            }
        }
    }




}