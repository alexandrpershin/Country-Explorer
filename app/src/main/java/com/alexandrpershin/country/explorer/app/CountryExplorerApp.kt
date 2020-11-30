package com.alexandrpershin.country.explorer.app

import android.app.Application
import com.alexandrpershin.country.explorer.ui.countrydetails.CountryDetailsDi
import com.alexandrpershin.country.explorer.ui.countrylist.CountriesListDi
import com.alexandrpershin.country.explorer.ui.search.SearchCountriesDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountryExplorerApp : Application() {

    private val modules = listOf(
        appModule,
        CountriesListDi.getModule(),
        CountryDetailsDi.getModule(),
        SearchCountriesDi.getModule()
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