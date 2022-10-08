package com_sergii_tymofieiev_mvi.ui.common.country_picker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sergii_tymofieiev.base_mvi.R
import com_sergii_tymofieiev_mvi.ui.common.list_items.ItemType
import com_sergii_tymofieiev_mvi.ui.common.view.ViewHolderBase
import com_sergii_tymofieiev_mvi.util.getFlagResource

/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
class CountryAdapter(
    @LayoutRes private var itemLayout: Int,
) : RecyclerView.Adapter<ViewHolderBase<Country>>() {

    private val items = mutableListOf<Country>()

    var onItemClickListener: ((Country) -> Unit)? = null
    override fun getItemViewType(position: Int): Int {
        return items[position].itemType.ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderBase<Country> {
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

    override fun onBindViewHolder(holder: ViewHolderBase<Country>, position: Int) {
        holder.bindData(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setup(items: List<Country>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class DelimiterViewHolder(view: View) : ViewHolderBase<Country>(view) {
        override fun bindData(item: Country, position: Int) {

        }

    }

    inner class ItemViewHolder(view: View) : ViewHolderBase<Country>(view) {

        private val imageViewFlag = view.findViewById<ImageView>(R.id.imageViewFlag)
        private val textViewName = view.findViewById<TextView>(R.id.textViewName)
        private val textViewCode = view.findViewById<TextView>(R.id.textViewCode)

        private var boundItem: Country? = null


        init {
            itemView.setOnClickListener {
                boundItem?.let {
                    onItemClickListener?.invoke(it)
                }
            }
        }




        override fun bindData(countryItem: Country, position: Int) {
            this.boundItem = countryItem
            imageViewFlag.setImageResource(getFlagResource(itemView,countryItem.iso2))
            textViewName.text = countryItem.name
            textViewCode.text = countryItem.getCodeWithPlus()
        }
    }

    inner class ItemDiffCallback(
        private val oldItems: List<Country>,
        private val newItems: List<Country>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldItems[oldPos].iso2 == newItems[newPos].iso2
        }

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldItems[oldPos].iso2 == newItems[newPos].iso2
        }
    }
}
