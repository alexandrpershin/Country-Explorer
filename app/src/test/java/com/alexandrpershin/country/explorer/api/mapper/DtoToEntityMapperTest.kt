package com.alexandrpershin.country.explorer.api.mapper

import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.api.TaskResult
import com.alexandrpershin.country.explorer.api.response.CountryResponse
import com.alexandrpershin.country.explorer.utils.JsonUtils
import org.junit.Assert
import org.junit.Test

class DtoToEntityMapperTest {

    @Test
    fun `test parse dto to entity list`() {
        val country = JsonUtils.provideTestCountry()
        val countryDto = JsonUtils.provideTestCountryResponse()
        val serverResponse = TaskResult.SuccessResult(listOf(countryDto))
        val parsedDto = mapToEntityList(serverResponse)

        Assert.assertEquals(country.name, (parsedDto as TaskResult.SuccessResult).data.first().name)
    }

    @Test
    fun `test parse dto to entity`() {
        val country = JsonUtils.provideTestCountry()
        val countryDto = JsonUtils.provideTestCountryResponse()
        val serverResponse = TaskResult.SuccessResult(countryDto)
        val parsedDto = mapToEntity(serverResponse)

        Assert.assertEquals(country.name, (parsedDto as TaskResult.SuccessResult).data.name)
    }

    @Test
    fun `test parse wrong dto to entity`() {
        val errorResponseResult = getErrorResponseResult<CountryResponse>()
        val parsedDto = mapToEntity(errorResponseResult)

        Assert.assertTrue(parsedDto is TaskResult.ErrorResult)
    }

    @Test
    fun `test parse wrong dto to entity list`() {
        val errorResponseResult = getErrorResponseResult<List<CountryResponse>>()
        val parsedDto = mapToEntityList(errorResponseResult)

        Assert.assertTrue(parsedDto is TaskResult.ErrorResult)
    }

    private fun <T> getErrorResponseResult(): TaskResult<T> {
        return TaskResult.ErrorResult(ErrorType.GenericError(message = "Bad request"))
    }

}