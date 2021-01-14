package com.alexandrpershin.country.explorer.ui.countrydetails

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.extentsions.getValueBlocking
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.testModule
import com.alexandrpershin.country.explorer.utils.JsonUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class CountryDetailsViewModelTest : KoinTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val module = module {
        factory {
            CountryDetailsViewModel(
                get(), COUNTRY_NAME, get(), testCoroutineDispatcher
            )
        }
    }

    /**
     * Allows LiveData value changes in unit tests without the need for Android SDK classes.
     */

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockKAnnotations.init(this)
        startKoin { modules(listOf(testModule, module)) }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `compose country description`() = runBlockingTest(testCoroutineDispatcher) {
        val country = JsonUtils.provideTestCountry()

        every { get<Application>().getString(R.string.country_description_sub_region_part, any(), any() ) } returns "Test location"
        every { get<Application>().getString(R.string.country_description_capital_part, any(), any()) } returns "Capital"
        every { get<Application>().getString(R.string.country_description_population_part, any(), any()) } returns "Population"
        every { get<Application>().getString(R.string.country_description_languages_part, any(), any()) } returns "Language"
        every { get<Application>().getString(R.string.country_description_currencies_part) } returns "Currencies"
        every { get<Application>().getString(R.string.country_description_borders_part) } returns "Borders"
        every { get<Application>().getString(R.string.country_description_code_part,any()) } returns "Country code"
        every { get<Application>().getString(R.string.country_description_phone_code_part) } returns "Country phone"
        every { get<Application>().getString(R.string.empty_space) } returns " "

        coEvery { get<CountryRepository>().getCountryByName(COUNTRY_NAME) } returns country
        coEvery { get<CountryRepository>().getAllCountriesSync() } returns listOf(country)

        val viewModel = get<CountryDetailsViewModel>()

        val description = viewModel.countryDescriptionLiveData.getValueBlocking()

        Assert.assertTrue(!description.isNullOrEmpty())
    }

    @Test
    fun `load country by name`() = runBlockingTest(testCoroutineDispatcher) {
        val country = JsonUtils.provideTestCountry()

        coEvery { get<CountryRepository>().getCountryByName(COUNTRY_NAME) } returns country

        val viewModel = get<CountryDetailsViewModel>()

        val actualCountry = viewModel.countryLiveData.getValueBlocking()

        Assert.assertEquals(country, actualCountry)
    }

    companion object {
        private const val COUNTRY_NAME = "Italy"
    }

}