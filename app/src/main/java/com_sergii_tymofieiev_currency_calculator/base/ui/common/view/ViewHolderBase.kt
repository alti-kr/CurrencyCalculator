package com_sergii_tymofieiev_currency_calculator.base.ui.common.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sergii Tymofieiev on 12.04.2022
 */
abstract class ViewHolderBase<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(item: T, position: Int)
}