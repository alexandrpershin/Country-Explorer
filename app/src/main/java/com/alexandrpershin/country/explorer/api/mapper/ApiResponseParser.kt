package com.alexandrpershin.country.explorer.api.mapper

import com.alexandrpershin.country.explorer.api.TaskResult

fun <T : List<DtoMapper<R>>, R> mapToEntityList(response: TaskResult<T>): TaskResult<List<R>> {
    return when (response) {
        is TaskResult.ErrorResult -> response
        is TaskResult.SuccessResult -> TaskResult.SuccessResult(response.data.map { it.toEntity() })
    }
}

fun <T : DtoMapper<R>, R> mapToEntity(response: TaskResult<T>): TaskResult<R> {
    return when (response) {
        is TaskResult.ErrorResult -> response
        is TaskResult.SuccessResult -> TaskResult.SuccessResult(response.data.toEntity())
    }

}