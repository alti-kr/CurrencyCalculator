package com_sergii_tymofieiev_mvi.data.api

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
    data class Success<T : Any>(val content: T) : NetworkResponse<T>()


    data class Error(val error: RetrofitException) : NetworkResponse<Nothing>()

    class RetrofitException(
        val errorType: ErrorType,
        val exception: Throwable?,
        val errorCode: Int,
        val mes: String?,
        val errorBody: String?,
        val errorData: ErrorResponseModel?
    ) : RuntimeException(mes, exception) {
        companion object {
            fun httpError(response: Response<*>): RetrofitException {
                val message = response.code().toString() + " " + response.message()
                val errorBody = response.errorBody()?.string()
                return RetrofitException(
                    errorType = ErrorType.HTTP,
                    exception = null,
                    errorCode = response.code(),
                    mes = message,
                    errorBody = errorBody,
                    errorData = handleErrorBody(errorBody)
                )
            }

            fun networkError(exception: IOException): RetrofitException {
                return RetrofitException(
                    errorType = ErrorType.NETWORK,
                    exception = exception,
                    errorCode = 0,
                    mes = exception.message,
                    errorBody = null,
                    errorData = null
                )
            }

            fun unexpectedError(exception: Throwable?): RetrofitException {
                val message =
                    if (exception == null) "HTTP error without error body" else exception.message
                return RetrofitException(
                    errorType = ErrorType.UNEXPECTED,
                    exception = exception,
                    errorCode = 0,
                    mes = message,
                    errorBody = null,
                    errorData = null
                )
            }

            private fun handleErrorBody(errorBody: String?): ErrorResponseModel? {
                return try {
                    val gson = Gson()
                    gson.fromJson(errorBody, ErrorResponseModel::class.java)
                } catch (e: Exception) {
                    null
                }
            }
        }

    }

    enum class ErrorType { HTTP, NETWORK, UNEXPECTED }
}