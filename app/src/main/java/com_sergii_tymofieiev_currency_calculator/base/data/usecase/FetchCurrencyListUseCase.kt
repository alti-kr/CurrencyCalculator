package com_sergii_tymofieiev_currency_calculator.base.data.usecase

import com_sergii_tymofieiev_currency_calculator.base.data.api.SuspendNetworkUseCase
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Sergii Tymofieiev on 08.10.2022
 */
abstract class FetchCurrencyListUseCase(
    defaultDispatcher: CoroutineDispatcher,
) : SuspendNetworkUseCase<Unit, List<CurrencyListItemModel>>(defaultDispatcher)