package com_sergii_tymofieiev_currency_calculator.base.ui.common.country_picker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes

import androidx.recyclerview.widget.RecyclerView
import com_sergii_tymofieiev_currency_calculator.R

import com_sergii_tymofieiev_currency_calculator.base.ui.common.list_items.ItemType
import com_sergii_tymofieiev_currency_calculator.base.ui.common.view.ViewHolderBase
import com_sergii_tymofieiev_currency_calculator.base.ui.main.main_fragment.data.CurrencyListItemModel
import com_sergii_tymofieiev_currency_calculator.base.util.getFlagResource

/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
class CountryAdapter(
    @LayoutRes private var itemLayout: Int,
) : RecyclerView.Adapter<ViewHolderBase<CurrencyListItemModel>>() {

    private val items = mutableListOf<CurrencyListItemModel>()

    var onItemClickListener: ((CurrencyListItemModel) -> Unit)? = null
    override fun getItemViewType(position: Int): Int {
        return items[position].itemType.ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderBase<CurrencyListItemModel> {
        val itemType = ItemType.getItemType(viewType)
        return if (itemType == ItemType.HEADER) {

            DelimiterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_delimiter, parent, false)
            )
        } else {
            ItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(itemLayout, parent, false)
            )

        }
    }

    override fun onBindViewHolder(holder: ViewHolderBase<CurrencyListItemModel>, position: Int) {
        holder.bindData(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setup(items: List<CurrencyListItemModel>) {

        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class DelimiterViewHolder(view: View) : ViewHolderBase<CurrencyListItemModel>(view) {
        override fun bindData(item: CurrencyListItemModel, position: Int) {

        }

    }

    inner class ItemViewHolder(view: View) : ViewHolderBase<CurrencyListItemModel>(view) {

        private val imageViewFlag = view.findViewById<ImageView>(R.id.imageViewFlag)
        private val textViewName = view.findViewById<TextView>(R.id.textViewName)
        private val textViewCode = view.findViewById<TextView>(R.id.textViewCode)

        private var boundItem: CurrencyListItemModel? = null


        init {
            itemView.setOnClickListener {
                boundItem?.let {
                    onItemClickListener?.invoke(it)
                }
            }
        }


        override fun bindData(countryItem: CurrencyListItemModel, position: Int) {
            this.boundItem = countryItem
            imageViewFlag.setImageResource(
                getFlagResource(
                    itemView,
                    countryItem.code.substring(0, 2).lowercase()
                )
            )
            textViewName.text = countryItem.name
            textViewCode.text = countryItem.code

        }
    }


}
