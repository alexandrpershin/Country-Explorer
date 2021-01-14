package com.alexandrpershin.country.explorer.ui.countrylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.extentsions.getValueBlocking
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.testModule
import com.alexandrpershin.country.explorer.utils.JsonUtils
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class CountriesListViewModelTest : KoinTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val module = module {
        factory {
            CountriesListViewModel(
                get(), get(), testCoroutineDispatcher
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
    fun `test load data from database`() = runBlockingTest(testCoroutineDispatcher) {
        val data = listOf(JsonUtils.provideTestCountry())
        coEvery { get<CountryRepository>().getAllCountriesSync() } returns data

        val successResult = TaskResult.SuccessResult(data)

        coEvery { get<CountryRepository>().fetchAllCountriesFromServer() } returns successResult
        every { get<CountryRepository>().getAllCountriesLiveData() } returns MutableLiveData(data)

        val viewModel = get<CountriesListViewModel>()

        val dataFromDb = viewModel.countriesLiveData.getValueBlocking()

        assertEquals(dataFromDb, data)
    }

    @Test
    fun `test empty data message`() = runBlockingTest(testCoroutineDispatcher) {
        val emptyList = listOf<Country>()
        coEvery { get<CountryRepository>().getAllCountriesLiveData() } returns MutableLiveData(emptyList)

        val mockObserver = spyk<Observer<Boolean>>()

        val viewModel = get<CountriesListViewModel>()
        viewModel.noResultsLiveData.observeForever(mockObserver)

        viewModel.loadCountriesFormServer()

        verify(atLeast = 1) { mockObserver.onChanged(true) }
    }

    @Test
    fun `test success response`() = runBlockingTest(testCoroutineDispatcher) {
        val data = listOf<Country>()
        val successResult = TaskResult.SuccessResult(data)

        coEvery { get<CountryRepository>().fetchAllCountriesFromServer() } returns successResult

        val viewModel = get<CountriesListViewModel>()

        viewModel.loadCountriesFormServer()

        coVerify(atLeast = 1) { get<CountryRepository>().saveCountries(data) }
    }

    @Test
    fun `test error response`() = runBlockingTest(testCoroutineDispatcher) {
        val data = listOf<Country>()
        val errorResponse = TaskResult.ErrorResult(ErrorType.GenericError(message = "Bad request"))

        coEvery { get<CountryRepository>().fetchAllCountriesFromServer() } returns errorResponse

        val viewModel = get<CountriesListViewModel>()

        viewModel.loadCountriesFormServer()

        coVerify(exactly = 0) { get<CountryRepository>().saveCountries(data) }
    }

    @Test
    fun `test no internet`() = runBlockingTest(testCoroutineDispatcher) {
        val error = ErrorType.InternetError(R.string.message_internet_error)
        val errorResponse = TaskResult.ErrorResult(error)

        coEvery { get<CountryRepository>().fetchAllCountriesFromServer() } returns errorResponse

        val mockObserver = spyk<Observer<ErrorType>>()

        val viewModel = get<CountriesListViewModel>()

        viewModel.errorNotifier.observeForever(mockObserver)

        viewModel.loadCountriesFormServer()

        coVerify(atLeast = 1) { mockObserver.onChanged(error) }
    }

    @Test
    fun `test loading`() = runBlockingTest(testCoroutineDispatcher) {
        val error = ErrorType.InternetError(R.string.message_internet_error)
        val errorResponse = TaskResult.ErrorResult(error)

        coEvery { get<CountryRepository>().fetchAllCountriesFromServer() } returns errorResponse

        val mockObserver = spyk<Observer<Boolean>>()

        val viewModel = get<CountriesListViewModel>()

        viewModel.loadingState.observeForever(mockObserver)

        viewModel.loadCountriesFormServer()

        coVerify(atLeast = 1) { mockObserver.onChanged(true) }
    }


}