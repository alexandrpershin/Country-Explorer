package com.alexandrpershin.country.explorer.app

import android.app.Application
import com.alexandrpershin.country.explorer.app.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountryExplorerApp : Application() {

    private val modules = listOf(
        appModule
    )


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CountryExplorerApp)
            modules(modules)
        }
    }


}