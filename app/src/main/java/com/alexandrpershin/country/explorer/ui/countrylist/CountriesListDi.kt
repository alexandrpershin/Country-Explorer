package com.alexandrpershin.country.explorer.ui.countrylist

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object CountriesListDi {
    fun getModule(): Module {
        return module {
            viewModel { CountriesListViewModel(get(), get()) }
        }
    }

}