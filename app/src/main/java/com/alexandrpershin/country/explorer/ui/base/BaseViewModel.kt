package com.alexandrpershin.country.explorer.ui.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.alexandrpershin.country.explorer.api.ErrorType
import com.alexandrpershin.country.explorer.utils.NavigationCommand
import com.alexandrpershin.country.explorer.utils.SingleLiveEvent
import java.io.Serializable

abstract class BaseViewModel : ViewModel() {

    val TAG = this.javaClass.simpleName

    private val _navigation: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigation
        get() = _navigation

    private val _errorNotifier: SingleLiveEvent<ErrorType> = SingleLiveEvent()
    val errorNotifier
        get() = _errorNotifier

    private val _loadingState: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val loadingState
        get() = _loadingState

    private val _forceKeyboardState: SingleLiveEvent<KeyboardState> = SingleLiveEvent()
    val forceKeyboardState
        get() = _forceKeyboardState

    private val _infoMessage: SingleLiveEvent<Int> = SingleLiveEvent()
    val infoMessage
        get() = _infoMessage

    @UiThread
    fun goTo(direction: Int) {
        _navigation.value = NavigationCommand.ToWithId(direction)
    }

    @UiThread
    fun goTo(directions: NavDirections) {
        _navigation.value = NavigationCommand.ToWithNavDirection(directions)
    }

    fun goBack() {
        _navigation.value = NavigationCommand.Back
    }

    fun goToWithArgs(destinationId: Int, args: Bundle) {
        _navigation.value = NavigationCommand.WithArgs(destinationId, args)
    }


    @UiThread
    fun notifyError(error: ErrorType) {
        _errorNotifier.value = error
    }

    @UiThread
    fun setBackResult(resultKey: String, result: Serializable) {
        _navigation.value = NavigationCommand.BackResult(resultKey, result)
    }

    fun showLoading() {
        _loadingState.postValue(true)
    }

    fun hideLoading() {
        _loadingState.postValue(false)
    }

    fun showInfoMessage(@StringRes message: Int) {
        _infoMessage.postValue(message)
    }

    @UiThread
    fun hideSoftKeyboard() {
        _forceKeyboardState.value = KeyboardState.Hide
    }

    @UiThread
    fun showSoftKeyboard() {
        _forceKeyboardState.value = KeyboardState.Show
    }

}

sealed class KeyboardState {
    object Hide : KeyboardState()
    object Show : KeyboardState()
}
