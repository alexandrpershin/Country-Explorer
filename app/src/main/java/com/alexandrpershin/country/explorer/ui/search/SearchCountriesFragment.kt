package com.alexandrpershin.country.explorer.ui.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.databinding.FragmentSearchCountriesBinding
import com.alexandrpershin.country.explorer.extensions.makeVisible
import com.alexandrpershin.country.explorer.extensions.setVisible
import com.alexandrpershin.country.explorer.extensions.showErrorMessage
import com.alexandrpershin.country.explorer.ui.base.BaseFragment
import com.alexandrpershin.country.explorer.ui.countrylist.adapter.CountriesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchCountriesFragment :
    BaseFragment<FragmentSearchCountriesBinding, SearchCountriesViewModel>() {

    override val viewModel: SearchCountriesViewModel by viewModel()

    private lateinit var adapter: CountriesAdapter

    override fun initComponents(binding: FragmentSearchCountriesBinding) {
        viewModel.showSoftKeyboard()
        initRecyclerView(binding)

        binding.search.onQueryChanged(clientTextChangedListener = { searchText ->
            viewModel.searchCountries(searchText)
        })

    }

    private fun initRecyclerView(binding: FragmentSearchCountriesBinding) {
        adapter = CountriesAdapter(requireContext())
        adapter.onItemClick = { name ->
            viewModel.goTo(SearchCountriesFragmentDirections.toCountryDetailsFragment(name))
        }

        binding.rvCountries.adapter = adapter
        binding.rvCountries.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun addListeners(binding: FragmentSearchCountriesBinding) {
        binding.ivBack.setOnClickListener {
            viewModel.goBack()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.hideSoftKeyboard()
    }

    override fun addObservers(binding: FragmentSearchCountriesBinding) {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        viewModel.noResultsLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvNoResults.setVisible(it)
        })

    }

    override fun errorHandler(errorType: ErrorType) {
        if (errorType is ErrorType.InternetError) {
            showErrorMessage(getString(errorType.resId))
        }

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchCountriesBinding =
        FragmentSearchCountriesBinding.inflate(inflater, container, false)
}
