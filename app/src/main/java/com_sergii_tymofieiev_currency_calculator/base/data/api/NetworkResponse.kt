package com_sergii_tymofieiev_currency_calculator.base.data.api

import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
sealed class NetworkResponse<out T : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val content: T) : com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<T>()


    data class Error(val error: com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException) : com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<Nothing>()

    class RetrofitException(
        val errorType: com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.ErrorType,
        val exception: Throwable?,
        val errorCode: Int,
        val mes: String?,
        val errorBody: String?,
        val errorData: com_sergii_tymofieiev_currency_calculator.base.data.api.ErrorResponseModel?
    ) : RuntimeException(mes, exception) {
        companion object {
            fun httpError(response: Response<*>): com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException {
                val message = response.code().toString() + " " + response.message()
                val errorBody = response.errorBody()?.string()
                return com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException(
                    errorType = com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.ErrorType.HTTP,
                    exception = null,
                    errorCode = response.code(),
                    mes = message,
                    errorBody = errorBody,
                    errorData = com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException.Companion.handleErrorBody(
                        errorBody
                    )
                )
            }

            fun networkError(exception: IOException): com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException {
                return com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException(
                    errorType = com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.ErrorType.NETWORK,
                    exception = exception,
                    errorCode = 0,
                    mes = exception.message,
                    errorBody = null,
                    errorData = null
                )
            }

            fun unexpectedError(exception: Throwable?): com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException {
                val message =
                    if (exception == null) "HTTP error without error body" else exception.message
                return com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException(
                    errorType = com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.ErrorType.UNEXPECTED,
                    exception = exception,
                    errorCode = 0,
                    mes = message,
                    errorBody = null,
                    errorData = null
                )
            }

            private fun handleErrorBody(errorBody: String?): com_sergii_tymofieiev_currency_calculator.base.data.api.ErrorResponseModel? {
                return try {
                    val gson = Gson()
                    gson.fromJson(errorBody, com_sergii_tymofieiev_currency_calculator.base.data.api.ErrorResponseModel::class.java)
                } catch (e: Exception) {
                    null
                }
            }
        }

    }

    enum class ErrorType { HTTP, NETWORK, UNEXPECTED }
}