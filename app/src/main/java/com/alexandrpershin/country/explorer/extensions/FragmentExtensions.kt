package com.alexandrpershin.country.explorer.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty


fun Fragment.showSuccessMessage(message: String) =
    Toasty.success(requireContext(), message, Toast.LENGTH_SHORT, true).show()


fun Fragment.showErrorMessage(message: String) =
    Toasty.error(requireContext(), message, Toast.LENGTH_SHORT, true).show()


fun Fragment.showInfoMessage(message: String) =
    Toasty.info(requireContext(), message, Toast.LENGTH_SHORT, true).show()


