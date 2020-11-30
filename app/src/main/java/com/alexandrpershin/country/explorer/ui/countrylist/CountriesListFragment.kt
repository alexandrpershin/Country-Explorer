package com.alexandrpershin.country.explorer.ui.countrylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.databinding.FragmentCountriesListBinding
import com.alexandrpershin.country.explorer.extensions.setVisible
import com.alexandrpershin.country.explorer.extensions.showErrorMessage
import com.alexandrpershin.country.explorer.extensions.stopRefreshing
import com.alexandrpershin.country.explorer.ui.base.BaseFragment
import com.alexandrpershin.country.explorer.ui.search.adapter.CountriesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesListFragment : BaseFragment<FragmentCountriesListBinding, CountriesListViewModel>() {

    private lateinit var adapter: CountriesAdapter

    override val viewModel: CountriesListViewModel by viewModel()

    override fun initComponents(binding: FragmentCountriesListBinding) {
        initToolbar(binding)
        initRecyclerView(binding)
    }

    private fun initToolbar(binding: FragmentCountriesListBinding) {
        binding.partialToolbar.toolbar.apply {
            title = getString(R.string.screen_title_countries)
            setImageAction(R.drawable.ic_search) {
                viewModel.goTo(CountriesListFragmentDirections.toSearchCountriesFragment())
            }
        }
    }

    private fun initRecyclerView(binding: FragmentCountriesListBinding) {
        adapter = CountriesAdapter(requireContext())
        adapter.onItemClick = { name: String ->
            viewModel.goTo(CountriesListFragmentDirections.toCountryDetailsFragment(name))
        }
        binding.rvCountries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCountries.adapter = adapter
    }

    override fun addListeners(binding: FragmentCountriesListBinding) {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.loadCountriesFormServer(true)
                stopRefreshing()
            }
        }
    }

    override fun addObservers(binding: FragmentCountriesListBinding) {
        viewModel.countriesLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvNoResults.setVisible(it.isEmpty())
            adapter.updateData(it)
        })
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.GenericError -> {
                showErrorMessage(errorType.message ?: getString(errorType.resId))
            }
            is ErrorType.InternetError -> {
                showErrorMessage(getString(errorType.resId))
                viewModel.fetchDataOnInternetAvailable()
            }
        }

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCountriesListBinding =
        FragmentCountriesListBinding.inflate(inflater, container, false)
}


