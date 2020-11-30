package com.alexandrpershin.country.explorer.service

import com.alexandrpershin.country.explorer.api.response.CountryResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {

    @GET("rest/v2/all/")
    fun getAllCountries() : Deferred<Response<List<CountryResponse>>>

    @GET("rest/v2/name/{$KEY_COUNTRY_NAME}")
    fun searchCountries(@Path(KEY_COUNTRY_NAME) countryName: String) : Deferred<Response<List<CountryResponse>>>


    companion object {
        private const val KEY_COUNTRY_NAME: String = "name"
    }

}