package com_sergii_tymofieiev_mvi.ui.common.country_picker

import android.content.Context
import java.io.IOException

/**
 * Created by Sergii Tymofieiev on 08.04.2022
 */
object FileReader {
    fun readAssetFile(context: Context, name: String): String? {
        return try {
            return context.assets.open(name)
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            null
        }
    }
}