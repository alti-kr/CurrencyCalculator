package com_sergii_tymofieiev_mvi.data.api

import com_sergii_tymofieiev_mvi.data.model.currency.CurrentCurrencyRateResponse
import retrofit2.http.GET

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
interface ApiService {
    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    suspend fun fetchCurrentCurrencyRate(): NetworkResponse<List<CurrentCurrencyRateResponse>>
}