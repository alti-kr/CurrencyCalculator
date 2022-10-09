package com_sergii_tymofieiev_currency_calculator.base.module

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com_sergii_tymofieiev_currency_calculator.BuildConfig
import com_sergii_tymofieiev_currency_calculator.base.data.api.NetworkResponseAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.min

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MiB
private const val CONNECT_TIMEOUT: Long = 60
private const val READ_TIMEOUT: Long = 60
private const val WRITE_TIMEOUT: Long = 60
private val step = 400
val networkModule = module {
    fun provideCache(application: Application): Cache {
        return Cache(application.cacheDir, CACHE_SIZE)
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor {
            var endInd = 0
            // this needs for long response is showing in logcat
            do {
                Log.d("HttpLoggingInterceptor", it.substring(endInd, min(endInd + step, it.length)))
                endInd += step
            } while (endInd <= it.length)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }



    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    fun provideHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: com_sergii_tymofieiev_currency_calculator.base.data.api.HttpAuthInterceptor,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(authInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))

            .build()
    }

    fun provideApi(retrofit: Retrofit): com_sergii_tymofieiev_currency_calculator.base.data.api.ApiService {
        return retrofit.create(com_sergii_tymofieiev_currency_calculator.base.data.api.ApiService::class.java)
    }

    fun provideHttpAuthInterceptor(): com_sergii_tymofieiev_currency_calculator.base.data.api.HttpAuthInterceptor {
        return com_sergii_tymofieiev_currency_calculator.base.data.api.HttpAuthInterceptor()
    }

    factory { provideCache(androidApplication()) }
    factory { provideHttpLoggingInterceptor() }
    factory { provideHttpAuthInterceptor() }
    factory { provideGson() }
    factory { provideHttpClient(get(), get(), get()) }
    single { provideRetrofit(get(), get()) }
    factory { provideApi(get()) }

}
