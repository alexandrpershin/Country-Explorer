package com.alexandrpershin.country.explorer.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexandrpershin.country.explorer.model.Country

/**
 * This class allows you to manipulate stored data.
 * */
@Dao
interface CountryDao {

    @Transaction
    fun saveCountries(countries: List<Country>) {
        deleteCountry()
        insertCountries(countries)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)

    @Query("SELECT * from Country WHERE name=:countryName")
    fun getCountryByName(countryName: String): Country

    @Query("SELECT * from Country")
    fun getAllCountriesSync(): List<Country>

    @Query("SELECT * from Country")
    fun getAllCountriesLiveData(): LiveData<List<Country>>

    @Query("DELETE FROM Country")
    fun deleteCountry()
}