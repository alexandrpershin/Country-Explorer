package com.alexandrpershin.country.explorer.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.extensions.asLiveData
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchCountriesViewModel(
    private val countryRepository: CountryRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    private var _searchResultLiveData = MutableLiveData<List<Country>>()
    val searchResultLiveData = _searchResultLiveData.asLiveData()

    fun searchCountries(query: String) {
        if (query.isEmpty()) {
            _searchResultLiveData.value = emptyList()
            return
        }

        viewModelScope.launch(coroutineContext) {
            showLoading()

            val result = countryRepository.searchCountries(query)

            when (result) {
                is TaskResult.ErrorResult -> {
                    hideLoading()
                    notifyError(result.errorType)

                    _searchResultLiveData.value = emptyList()
                }
                is TaskResult.SuccessResult -> {
                    hideLoading()
                    _searchResultLiveData.value = result.data

                }
            }
        }
    }


}