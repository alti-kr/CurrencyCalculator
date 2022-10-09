package com_sergii_tymofieiev_currency_calculator.base.data.mapper

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
interface Mapper<I, O> {
    fun map(input: I): O
}