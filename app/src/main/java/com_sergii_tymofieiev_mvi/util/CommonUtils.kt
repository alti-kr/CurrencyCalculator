package com_sergii_tymofieiev_mvi.util

import android.os.Parcelable
import android.os.SystemClock
import android.view.View
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.sergii_tymofieiev.base_mvi.BuildConfig
import java.util.*

/**
 * Created by Sergii Tymofieiev on 20.03.2022
 */
fun isDebug(): Boolean {
    return BuildConfig.BUILD_TYPE == "debug"
}
class SafeClickListener(
    private val onSafeCLick: (View) -> Unit,
) : View.OnClickListener {


    override fun onClick(v: View) {

        onSafeCLick(v)
    }
}

private var lastTimeClicked: Long = 0
private var defaultInterval: Int = 500
fun View.safeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        if (SystemClock.elapsedRealtime() - lastTimeClicked >= defaultInterval) {
            lastTimeClicked =
                SystemClock.elapsedRealtime()
            onSafeClick(it)
        }
    }
    setOnClickListener(safeClickListener)
}
fun <T> deserialize(serializedData: String, classOfT: Class<T>): T? {
    return try {
        val gson = Gson()
        gson.fromJson(serializedData, classOfT)
    } catch (e: JsonSyntaxException) {
        null
    }
}

fun serialize(objects: Parcelable?): String? {
    return if (objects == null) {
        null
    } else {
        val gson = Gson()
        gson.toJson(objects)
    }
}

const val ASSET_FILE_NAME = "countries.json"
fun getFlagResource(itemView: View, iso2: String?): Int {
    return itemView.context.resources.getIdentifier(
        "country_flag_$iso2",
        "drawable",
        itemView.context.packageName
    )
}

val headerList = listOf("se", "gb", "us")

