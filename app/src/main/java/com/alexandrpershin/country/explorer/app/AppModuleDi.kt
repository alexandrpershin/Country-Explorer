package com.alexandrpershin.country.explorer.app

import com.alexandrpershin.country.explorer.api.BackendApiFactory
import com.alexandrpershin.country.explorer.api.createApiService
import com.alexandrpershin.country.explorer.persistence.LocalDatabase
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.repository.repositoryImpl.CountryRepositoryImpl
import com.alexandrpershin.country.explorer.service.CountriesService
import com.alexandrpershin.country.explorer.utils.NetworkStatusHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule: Module = module {

    //Retrofit
    single<Retrofit> { BackendApiFactory().provideRetrofit(get()) }

    //Local database
    single<LocalDatabase> { LocalDatabase.getInstance(androidApplication()) }

    //NetworkStatusHelper
    single<NetworkStatusHelper> { NetworkStatusHelper(androidApplication()) }

    //Repository
    single<CountryRepository> { CountryRepositoryImpl(get(), get()) }

    //API service
    single<CountriesService> { createApiService<CountriesService>(get()) }
}