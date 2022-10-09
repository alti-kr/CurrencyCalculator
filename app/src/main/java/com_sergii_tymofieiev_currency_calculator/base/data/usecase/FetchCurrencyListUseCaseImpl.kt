package com_sergii_tymofieiev_currency_calculator.base.data.usecase

import com_sergii_tymofieiev_currency_calculator.base.data.repository.currency.CurrencyRepository
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
class FetchCurrencyListUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher,
    private val currencyRepository: CurrencyRepository,
):FetchCurrencyListUseCase(defaultDispatcher) {
    override suspend fun execute(parameter: Unit): com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponse<List<CurrencyListItemModel>> {
        return currencyRepository.fetchCurrencyList()
    }
}