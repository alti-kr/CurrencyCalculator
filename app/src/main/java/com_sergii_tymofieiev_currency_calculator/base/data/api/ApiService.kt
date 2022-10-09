package com_sergii_tymofieiev_currency_calculator.base.data.api
import com_sergii_tymofieiev_currency_calculator.base.data.model.currency.CurrentCurrencyRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
interface ApiService {
    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    suspend fun fetchCurrentCurrencyRate():NetworkResponse<List<CurrentCurrencyRateResponse>>

    @GET("NBUStatService/v1/statdirectory/exchange?json")
    suspend fun fetchRateByDate(@Query("date") date: String):NetworkResponse<List<CurrentCurrencyRateResponse>>
}