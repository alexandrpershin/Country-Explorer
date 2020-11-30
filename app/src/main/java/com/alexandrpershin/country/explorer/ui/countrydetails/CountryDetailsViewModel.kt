package com.alexandrpershin.country.explorer.ui.countrydetails

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.extensions.asLiveData
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.ui.base.BaseViewModel
import com.alexandrpershin.country.explorer.utils.getLocalizedCountryName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CountryDetailsViewModel(
    private val context: Context,
    private val countryName: String,
    private val repository: CountryRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    private var _countryLiveData = MutableLiveData<Country>()
    val countryLiveData = _countryLiveData.asLiveData()

    private var _countryDescriptionLiveData = MutableLiveData<String>()
    val countryDescriptionLiveData = _countryDescriptionLiveData.asLiveData()

    init {
        loadCountryByName(countryName)
    }

    private fun loadCountryByName(countryName: String) {
        viewModelScope.launch(coroutineContext) {
            val country = repository.getCountryByName(countryName)
            _countryLiveData.value = country

            composeDescription(country)
        }
    }


    /**
     * Creates description of country using common template.
    * */

    private suspend fun composeDescription(country: Country) {
        val allCountries = repository.getAllCountriesSync()

        val borderCountriesNames =
            allCountries.filter { country.borders.contains(it.countryCode) }.map { it.name }

        val locationBlock = context.getString(
            R.string.country_description_sub_region_part,
            getLocalizedCountryName(context, country),
            country.subRegion
        )

        val capitalBlock = context.getString(
            R.string.country_description_capital_part,
            country.name,
            country.capital
        )

        val populationBlock = context.getString(
            R.string.country_description_population_part,
            country.population,
            country.name
        )

        val languagesBlock = context.getString(
            R.string.country_description_languages_part,
            country.languages.size,
            country.languages.joinToString()
        )

        val currenciesBlock = context.getString(
            R.string.country_description_currencies_part,
            country.name,
            country.currencies.size,
            country.currencies.joinToString()
        )

        val bordersBlock =
            context.getString(
                R.string.country_description_borders_part,
                country.name,
                country.borders.size,
                borderCountriesNames.joinToString()
            )

        val countryCodeBlock =
            context.getString(R.string.country_description_code_part, country.countryCode)

        val phoneCodeBlock =  context.getString(R.string.country_description_phone_code_part, country.phoneCode)

        val emptySpace = context.getString(R.string.empty_space)

        val description = StringBuilder()
            .append(locationBlock)
            .append(emptySpace)
            .append(capitalBlock)
            .append(emptySpace)
            .append(populationBlock)
            .append(emptySpace)
            .append(languagesBlock)
            .append(emptySpace)
            .append(currenciesBlock)
            .append(emptySpace)
            .append(bordersBlock)
            .append(emptySpace)
            .append(countryCodeBlock)
            .append(emptySpace)
            .append(phoneCodeBlock)

        _countryDescriptionLiveData.value = description.toString()
    }


}