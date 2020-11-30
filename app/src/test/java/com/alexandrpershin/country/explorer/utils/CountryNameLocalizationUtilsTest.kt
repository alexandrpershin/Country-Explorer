package com.alexandrpershin.country.explorer.utils

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test


class CountryNameLocalizationUtilsTest {

    @Test
    fun `test default country name`() {
        val context = mockk<Context>()

        every { context.resources.configuration.locales.get(0).language } returns "en"

        val country = JsonUtils.provideTestCountry()
        val localizedCountryName = getLocalizedCountryName(context, country)

        assertEquals(country.name, localizedCountryName)
    }

    @Test
    fun `test german localized country name`() {
        val context = mockk<Context>()

        val germanCountryTranslation = "Italien"
        val germanLocale = "de"

        every { context.resources.configuration.locales.get(0).language } returns germanLocale

        val country = JsonUtils.provideTestCountry()
        val localizedCountryName = getLocalizedCountryName(context, country)

        assertEquals(germanCountryTranslation, localizedCountryName)
    }

    @Test
    fun `test spanish localized country name`() {
        val context = mockk<Context>()

        val spanishCountryTranslation = "Italia"
        val spanishLocale = "es"

        every { context.resources.configuration.locales.get(0).language } returns spanishLocale

        val country = JsonUtils.provideTestCountry()
        val localizedCountryName = getLocalizedCountryName(context, country)

        assertEquals(spanishCountryTranslation, localizedCountryName)
    }

    @Test
    fun `test not existing translation`() {
        val context = mockk<Context>()

        val dummyLocale = "dummy"
        every { context.resources.configuration.locales.get(0).language } returns dummyLocale

        val country = JsonUtils.provideTestCountry()
        val localizedCountryName = getLocalizedCountryName(context, country)

        assertEquals(country.name, localizedCountryName)
    }


}