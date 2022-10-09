package com_sergii_tymofieiev_currency_calculator.base.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
suspend fun <T> default(
    block: suspend () -> T
) = withContext(Dispatchers.Default) {
    block.invoke()
}

suspend fun <T> io(
    block: suspend () -> T
) = withContext(Dispatchers.IO) {
    block.invoke()
}
