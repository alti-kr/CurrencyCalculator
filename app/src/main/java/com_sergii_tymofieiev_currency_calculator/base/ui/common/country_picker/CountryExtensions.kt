package com_sergii_tymofieiev_currency_calculator.base.ui.common.country_picker

import com_sergii_tymofieiev_currency_calculator.base.ui.common.list_items.ItemType
import org.json.JSONArray
import kotlin.collections.ArrayList

/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
fun String?.toCountryList(): List<Country> {
    val countries = mutableListOf<Country>()
    try {
        val json = JSONArray(this)
        for (i in 0 until json.length()) {
            val country = json.getJSONObject(i)
            countries.add(
                Country(
                    type = ItemType.ITEM,
                    iso2 = country.getString("iso2"),
                    name = country.getString("name"),
                    code = country.getInt("code")
                )
            )
        }
    } catch (e: Exception) {
        // ignored
    }
    return countries
}

fun List<Country>.addHeaderSection(admittedToHeader: List<String>): List<Country> {
    return if (admittedToHeader.isEmpty()) {
        this
    } else {
        val headerList = this.filter { admittedToHeader.contains(it.iso2) }
        val bottomList = this.filterNot { admittedToHeader.contains(it.iso2) }
        val resultList = ArrayList<Country>()
        var tCounty: Country?
        admittedToHeader.forEach {
            tCounty = headerList.findByIso(it)
            if (tCounty != null) {
                resultList.add(tCounty!!)
            }
        }
        if (resultList.isNotEmpty()) {
            resultList.add(Country(ItemType.HEADER, "", "", 0))
        }
        resultList.addAll(bottomList)
        resultList
    }
}

private fun List<Country>.findByIso(isoCode: String): Country? {
    this.forEach {
        if (it.iso2 == isoCode) {
            return it
        }
    }
    return null
}
