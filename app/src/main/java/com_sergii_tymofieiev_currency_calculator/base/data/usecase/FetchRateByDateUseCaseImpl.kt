package com_sergii_tymofieiev_currency_calculator.base.data.usecase

import com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse
import com_sergii_tymofieiev_currency_calculator.base.data.repository.currency.CurrencyRepository
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Sergii Tymofieiev on 09.10.2022
 */
class FetchRateByDateUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher,
    private val currencyRepository: CurrencyRepository,
):FetchRateByDateUseCase(defaultDispatcher)
{
    override suspend fun execute(parameter: String): NetworkResponse<List<CurrencyListItemModel>> {
        return currencyRepository.fetchCurrencyListByDate(parameter)
    }
}