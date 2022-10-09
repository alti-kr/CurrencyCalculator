package com_sergii_tymofieiev_currency_calculator.base.data.api

import okhttp3.Request
import okio.Timeout
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
internal class NetworkResponseCall<S : Any>(
    private val delegate: Call<S>
) : Call<com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<S>>, KoinComponent {
    override fun enqueue(callback: Callback<com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.Success(body))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(
                            com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.Error(
                                com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException.httpError(response)))
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.Error(
                        com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException.networkError(throwable))
                    else -> com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.Error(
                        com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException.unexpectedError(throwable))
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }
    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
