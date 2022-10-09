package com_sergii_tymofieiev_currency_calculator.base.data.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
abstract class SuspendNetworkUseCase<in P, out R : Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: P): com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters)
            }
        } catch (e: Exception) {
            com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.Error(
                com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse.RetrofitException.unexpectedError(e))
        }
    }

    protected abstract suspend fun execute(parameter: P): com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<R>
}