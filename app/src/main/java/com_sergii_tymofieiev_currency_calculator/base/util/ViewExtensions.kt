package com_sergii_tymofieiev_currency_calculator.base.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(
    @LayoutRes layoutId: Int,
    attachToRoot: Boolean = false,
): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
