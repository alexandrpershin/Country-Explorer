package com.alexandrpershin.country.explorer.ui.countrydetails

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Provides  [CountryDetailsViewModel] via [Koin]
 * */

object CountryDetailsDi {
    fun getModule(): Module {
        return module {
            viewModel { (countryName: String) ->
                CountryDetailsViewModel(
                    androidApplication(),
                    countryName,
                    get()
                )
            }
        }
    }

}