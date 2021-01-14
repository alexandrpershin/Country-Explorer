package com.alexandrpershin.country.explorer

import android.app.Application
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.utils.NetworkHelper
import io.mockk.mockk
import org.koin.dsl.module

/**
 * Koin module common to all unit tests.
 */
val testModule = module {
    single { mockk<NetworkHelper>(relaxed = true) }
    single { mockk<CountryRepository>(relaxed = true) }
    single { mockk<Application>(relaxed = true) }
}
