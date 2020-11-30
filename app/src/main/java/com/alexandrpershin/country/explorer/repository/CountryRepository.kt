package com.alexandrpershin.country.explorer.repository

import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.model.Country

/**
 * Repository interface for providing data to ViewModel
 * */

interface CountryRepository {

    suspend fun fetchAllCountriesFromServer(): TaskResult<List<Country>>

    suspend fun getAllCountriesSync(): List<Country>

    suspend fun saveCountries(countries: List<Country>)

    suspend fun searchCountries(countryName: String): TaskResult<List<Country>>

    suspend fun getCountryByName(countryName: String): Country
}