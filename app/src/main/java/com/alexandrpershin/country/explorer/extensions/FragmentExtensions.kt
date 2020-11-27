package com.alexandrpershin.country.explorer.extensions

import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty


fun Fragment.showSuccessMessage(message: String) {
    Toasty.success(requireContext(), message, Toast.LENGTH_SHORT, true).apply {
        setToastGravityTop(this)
    }.show()
}

private fun setToastGravityTop(toast: Toast) {
    toast.setGravity(Gravity.TOP, 0, 32)
}

fun Fragment.showErrorMessage(message: String) {
    Toasty.error(requireContext(), message, Toast.LENGTH_SHORT, true).apply {
        setToastGravityTop(this)
    }.show()
}

fun Fragment.showInfoMessage(message: String) {
    Toasty.info(requireContext(), message, Toast.LENGTH_SHORT, true).apply {
        setToastGravityTop(this)
    }.show()
}

