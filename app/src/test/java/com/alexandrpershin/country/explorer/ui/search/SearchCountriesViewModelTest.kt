package com.alexandrpershin.country.explorer.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.repository.CountryRepository
import com.alexandrpershin.country.explorer.testModule
import com.alexandrpershin.country.explorer.utils.JsonUtils
import io.mockk.*
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

class SearchCountriesViewModelTest : KoinTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val module = module {
        factory {
            SearchCountriesViewModel(
                get(), testCoroutineDispatcher
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
    fun `test search - empty result`() = runBlockingTest(testCoroutineDispatcher) {
        val emptyList = listOf<Country>()
        val emptyResponse = TaskResult.SuccessResult(emptyList)
        coEvery { get<CountryRepository>().searchCountries(any()) } returns emptyResponse

        val mockObserver = spyk<Observer<Boolean>>()

        val viewModel = get<SearchCountriesViewModel>()
        viewModel.noResultsLiveData.observeForever(mockObserver)

        viewModel.searchCountries("dummyCountry")

        verify(atLeast = 1) { mockObserver.onChanged(true) }
    }

    @Test
    fun `test search with result`() = runBlockingTest(testCoroutineDispatcher) {
        val data = listOf(JsonUtils.provideTestCountry())
        val response = TaskResult.SuccessResult(data)
        coEvery { get<CountryRepository>().searchCountries(any()) } returns response

        val mockObserver = spyk<Observer<List<Country>>>()

        val viewModel = get<SearchCountriesViewModel>()
        viewModel.searchResultLiveData.observeForever(mockObserver)

        viewModel.searchCountries("dummyCountry")

        verify(atLeast = 1) { mockObserver.onChanged(data) }
    }

    @Test
    fun `test show loading`() = runBlockingTest(testCoroutineDispatcher) {
        val data = listOf(JsonUtils.provideTestCountry())
        val response = TaskResult.SuccessResult(data)
        coEvery { get<CountryRepository>().searchCountries(any()) } returns response

        val mockObserver = spyk<Observer<Boolean>>()

        val viewModel = get<SearchCountriesViewModel>()
        viewModel.loadingState.observeForever(mockObserver)

        viewModel.searchCountries("dummyCountry")

        verify(atLeast = 1) { mockObserver.onChanged(true) }
    }

    @Test
    fun `test no internet`() = runBlockingTest(testCoroutineDispatcher) {
        val error = ErrorType.InternetError(R.string.message_internet_error)
        val errorResponse = TaskResult.ErrorResult(error)

        coEvery { get<CountryRepository>().searchCountries(any()) } returns errorResponse

        val mockObserver = spyk<Observer<ErrorType>>()

        val viewModel = get<SearchCountriesViewModel>()

        viewModel.errorNotifier.observeForever(mockObserver)

        viewModel.searchCountries("dummyCountry")

        coVerify(atLeast = 1) { mockObserver.onChanged(error) }
    }

}