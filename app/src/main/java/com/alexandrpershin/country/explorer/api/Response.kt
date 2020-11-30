package com.alexandrpershin.country.explorer.api

import androidx.annotation.StringRes
import com.alexandrpershin.country.explorer.R

/**
 * Comfortable way to wrap data and errors
 * */

sealed class TaskResult<out T> {
    data class ErrorResult(val errorType: ErrorType) : TaskResult<Nothing>()
    data class SuccessResult<T>(val data: T) : TaskResult<T>()
}

sealed class ErrorType {
    data class GenericError(
        @StringRes val resId: Int = R.string.error_message_unexpected_error,
        val message: String? = null
    ) : ErrorType()

    data class InternetError(@StringRes val resId: Int) : ErrorType()
}
