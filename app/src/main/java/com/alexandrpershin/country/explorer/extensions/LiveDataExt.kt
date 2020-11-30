package com.alexandrpershin.country.explorer.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}
