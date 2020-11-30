package com.alexandrpershin.country.explorer.repository.repositoryImpl

import androidx.lifecycle.LiveData
import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.api.executeAsyncRequest
import com.alexandrpershin.country.explorer.api.mapper.mapToEntityList
import com.alexandrpershin.country.explorer.api.response.CountryResponse
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.persistence.LocalDatabase
import com.alexandrpershin.country.explorer.persistence.dao.CountryDao
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.service.CountriesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CountryRepositoryImpl(
    private val countriesService: CountriesService,
    private val localDatabase: LocalDatabase,
    private val countryDao: CountryDao = localDatabase.countryDao(),
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) : CountryRepository {

    override suspend fun fetchAllCountriesFromServer(): TaskResult<List<Country>> =
        withContext(coroutineContext) {
            val response: TaskResult<List<CountryResponse>> =
                executeAsyncRequest(countriesService.getAllCountries())
            return@withContext mapToEntityList(response)
        }

    override suspend fun getAllCountriesSync(): List<Country> = withContext(coroutineContext) {
        return@withContext countryDao.getAllCountriesSync()
    }

    override suspend fun saveCountries(countries: List<Country>) = withContext(coroutineContext) {
        countryDao.saveCountries(countries)
    }

    override suspend fun searchCountries(countryName: String): TaskResult<List<Country>> =
        withContext(coroutineContext) {
            val response: TaskResult<List<CountryResponse>> =
                executeAsyncRequest(countriesService.searchCountries(countryName))

            return@withContext mapToEntityList(response)
        }

    override suspend fun getCountryByName(countryName: String): Country =
        withContext(coroutineContext) {
            return@withContext countryDao.getCountryByName(countryName)
        }
}


