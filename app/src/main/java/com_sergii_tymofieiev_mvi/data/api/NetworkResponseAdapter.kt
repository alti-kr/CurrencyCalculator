package com_sergii_tymofieiev_mvi.data.api

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
class NetworkResponseAdapter<S : Any, E : Any>(
    private val successType: Type,
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call)
    }
}