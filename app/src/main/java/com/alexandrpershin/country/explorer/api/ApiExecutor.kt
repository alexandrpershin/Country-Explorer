package com.alexandrpershin.country.explorer.api


import android.util.Log
import com.alexandrpershin.country.explorer.R
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.UnknownHostException

/**
 * Handles retrofit response. Depends on result code returns expected result if success and [ErrorType] otherwise
 * */

suspend fun <T> executeAsyncRequest(request: Deferred<Response<T>>): TaskResult<T> {

    return try {
        val result = request.await()

        if (result.isSuccessful) {
            Log.d(TAG, "Result is successful")
            TaskResult.SuccessResult(data = result.body() as T)
        } else {
            Log.d(TAG, "Result is not successful, code: ${result.code()} ")
            TaskResult.ErrorResult(ErrorType.GenericError(resId = R.string.error_message_unexpected_error))
        }
    } catch (ex: Exception) {
        Log.d(TAG, "Exception message: ${ex.message}")
        if (ex is UnknownHostException) {
            TaskResult.ErrorResult(ErrorType.InternetError(R.string.message_internet_error))
        } else {
            TaskResult.ErrorResult(ErrorType.GenericError(message = ex.localizedMessage))
        }
    }
}


private const val TAG = "ApiExecutor"
