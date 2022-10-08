package com_sergii_tymofieiev_mvi.data.api

import okhttp3.*
import org.koin.core.component.KoinComponent

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
class HttpAuthInterceptor : Interceptor, KoinComponent {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}