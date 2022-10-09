package com_sergii_tymofieiev_currency_calculator.base.data.pref.pref_data

import android.content.SharedPreferences
import android.os.Parcelable
import androidx.core.content.edit
import com_sergii_tymofieiev_currency_calculator.base.util.deserialize
import com_sergii_tymofieiev_currency_calculator.base.util.serialize
import kotlin.reflect.KProperty

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
class ParcelablePreference<T>(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val classOfT: Class<T>
) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T? {
        val data = preferences.value.getString(name, null)
        return if (data != null) {
            return deserialize(data, classOfT)
        } else {
            null
        }
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Parcelable?) {
        preferences.value.edit { putString(name, serialize(value)) }
    }
}