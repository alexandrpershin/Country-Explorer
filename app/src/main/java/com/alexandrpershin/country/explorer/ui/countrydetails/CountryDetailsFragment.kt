package com.alexandrpershin.country.explorer.ui.countrydetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.databinding.FragmentCountryDetailsBinding
import com.alexandrpershin.country.explorer.extensions.loadSvgFromUrl
import com.alexandrpershin.country.explorer.extensions.showErrorMessage
import com.alexandrpershin.country.explorer.ui.base.BaseFragment
import com.alexandrpershin.country.explorer.ui.cutomview.ToolbarPrimaryButton
import com.alexandrpershin.country.explorer.utils.getLocalizedCountryName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CountryDetailsFragment :
    BaseFragment<FragmentCountryDetailsBinding, CountryDetailsViewModel>() {

    private val args: CountryDetailsFragmentArgs by navArgs()

    override val viewModel: CountryDetailsViewModel by viewModel { parametersOf(args.countryName) }

    override fun initComponents(binding: FragmentCountryDetailsBinding) {
        initToolbar(binding)
    }

    private fun initToolbar(binding: FragmentCountryDetailsBinding) {
        binding.partialToolbar.toolbar.apply {
            title = getString(R.string.screen_title_country_details)
            setNavigationIcon(ToolbarPrimaryButton.Type.Back) {
                viewModel.goBack()
            }
        }
    }

    override fun addListeners(binding: FragmentCountryDetailsBinding) {
    }

    override fun addObservers(binding: FragmentCountryDetailsBinding) {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->

            country?.let {
                binding.ivCountryFlag.loadSvgFromUrl(
                    requireContext(),
                    it.flag,
                    placeholderRes = R.drawable.ic_country_flag_placeholder
                )

                binding.tvCountryName.text = getLocalizedCountryName(requireContext(), it)
            }

        })

        viewModel.countryDescriptionLiveData.observe(viewLifecycleOwner, Observer { description ->
            binding.tvCountryDescription.text = description
        })

    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.GenericError -> {
                showErrorMessage(errorType.message ?: getString(errorType.resId))
            }
            is ErrorType.InternetError -> {
                showErrorMessage(getString(errorType.resId))
            }
        }

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCountryDetailsBinding =
        FragmentCountryDetailsBinding.inflate(inflater, container, false)
}