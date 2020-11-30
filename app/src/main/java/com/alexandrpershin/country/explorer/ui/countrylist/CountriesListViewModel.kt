package com.alexandrpershin.country.explorer.ui.countrylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.extensions.asLiveData
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.ui.base.BaseViewModel
import com.alexandrpershin.country.explorer.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CountriesListViewModel(
    private val countryRepository: CountryRepository,
    private val networkStatusHelper: NetworkHelper,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    init {
        loadCountriesFormServer()
    }

    private var _countriesLiveData = MutableLiveData<List<Country>>()
    val countriesLiveData = _countriesLiveData.asLiveData()

    fun loadCountriesFormServer() {
        viewModelScope.launch(coroutineContext) {
            showLoading()

            val result = countryRepository.fetchAllCountriesFromServer()

            when (result) {
                is TaskResult.ErrorResult -> {
                    hideLoading()
                    notifyError(result.errorType)
                }
                is TaskResult.SuccessResult -> {
                    hideLoading()
                    countryRepository.saveCountries(result.data)

                    loadCountriesFromDatabase()
                }
            }
        }
    }

    private fun loadCountriesFromDatabase() {
        viewModelScope.launch(coroutineContext) {
            _countriesLiveData.value = countryRepository.getAllCountriesSync()
        }
    }

    fun fetchDataOnInternetAvailable() {
        networkStatusHelper.apply {
            setCallbackOnInternetAvailable {
                loadCountriesFormServer()
                showInfoMessage(R.string.message_you_back_online)
                this.removeCallbackOnInternetAvailable()
            }

        }
    }

}