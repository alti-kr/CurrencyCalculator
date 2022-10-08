package com_sergii_tymofieiev_mvi.ui.common.list_items

/**
 * Created by Sergii Tymofieiev on 12.04.2022
 */
enum class ItemType {
    HEADER, ITEM,
    ;

    companion object {
        fun getItemType(ordinal: Int): ItemType? {
            for (itemType in values()) {
                if (itemType.ordinal == ordinal) {
                    return itemType
                }
            }

            return null
        }
    }
}