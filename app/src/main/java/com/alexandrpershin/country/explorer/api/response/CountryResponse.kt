package com.alexandrpershin.country.explorer.api.response

import com.alexandrpershin.country.explorer.api.mapper.Mapper
import com.alexandrpershin.country.explorer.extensions.toSafeString
import com.alexandrpershin.country.explorer.model.Country


data class CountryResponse(
    val alpha2Code: String?,
    val alpha3Code: String,
    val altSpellings: List<String>,
    val area: Double,
    val borders: List<String>?,
    val callingCodes: List<String>?,
    val capital: String?,
    val cioc: String,
    val currencies: List<Currency>?,
    val demonym: String,
    val flag: String?,
    val gini: Double,
    val languages: List<Language>?,
    val latlng: List<Double>,
    val name: String?,
    val nativeName: String?,
    val numericCode: String,
    val population: Int?,
    val region: String,
    val regionalBlocs: List<RegionalBloc>,
    val subregion: String?,
    val timezones: List<String>,
    val topLevelDomain: List<String>,
    val translations: Translations
) : Mapper<Country> {

    override fun toEntity(): Country {
        return Country(
            name = name.toSafeString(),
            nativeName = nativeName.toSafeString(),
            flag = flag.toSafeString(),
            capital = capital.toSafeString(),
            phoneCode = callingCodes?.joinToString().toSafeString(),
            countryCode = alpha3Code.toSafeString(),
            population = population ?: 0,
            subregion = subregion.toSafeString(),
            languages = languages?.map { it.name } ?: emptyList(),
            borders = borders ?: emptyList(),
            currencies = currencies?.map { "${it.name}, ${it.symbol}" } ?: emptyList(),
        )
    }

}

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

data class Language(
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
)

data class RegionalBloc(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<Any>,
    val otherNames: List<String>
)

data class Translations(
    val br: String,
    val de: String,
    val es: String,
    val fa: String,
    val fr: String,
    val hr: String,
    val `it`: String,
    val ja: String,
    val nl: String,
    val pt: String
)

