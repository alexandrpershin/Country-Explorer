package com.alexandrpershin.country.explorer.utils

import android.content.Context
import com.alexandrpershin.country.explorer.model.Country

/**
 * Checks if localized name is available, otherwise returns default country name
 * */

fun getLocalizedCountryName(context: Context, country: Country): String {
    val language = context.resources.configuration.locales.get(0).language

    return country.translations[language] ?: country.name
}
