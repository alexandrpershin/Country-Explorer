package com.alexandrpershin.country.explorer.ui.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Provides [SearchCountriesViewModel] via [Koin]
 * */

object SearchCountriesDi {
    fun getModule(): Module {
        return module {
            viewModel { SearchCountriesViewModel(get()) }
        }
    }

}