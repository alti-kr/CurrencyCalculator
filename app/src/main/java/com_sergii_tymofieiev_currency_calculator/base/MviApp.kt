package com_sergii_tymofieiev_currency_calculator.base

import android.app.Application
import com_sergii_tymofieiev_currency_calculator.base.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

/**
 * Created by Sergii Tymofieiev on 22.03.2022
 */
class MviApp: Application() {
    companion object {
        lateinit var MviAppApplication: MviApp
    }

    override fun onCreate() {
        super.onCreate()
        MviAppApplication = this
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MviApp)
            modules(
                networkModule,
                preferenceModule,
                repositoryModule,
                mapperModule,
                useCaseModule,
                viewModelModule,
            )
        }
    }
}