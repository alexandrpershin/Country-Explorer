package com.alexandrpershin.country.explorer.api.response

import com.alexandrpershin.country.explorer.api.mapper.Mapper
import com.alexandrpershin.country.explorer.extensions.toSafeString
import com.alexandrpershin.country.explorer.model.Country
import com.google.gson.annotations.SerializedName


class CountryResponse(
    val alpha3Code: String?,
    val borders: List<String>?,
    val callingCodes: List<String>?,
    val capital: String?,
    val currencies: List<CurrencyDto>?,
    val flag: String?,
    val languages: List<LanguageDto>?,
    val name: String?,
    val nativeName: String?,
    val numericCode: String?,
    val population: Int?,
    @SerializedName("subregion") val subRegion: String?,
    val translations: Map<String, String>?
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
            subRegion = subRegion.toSafeString(),
            languages = languages?.map { it.name.toSafeString() } ?: emptyList(),
            borders = borders ?: emptyList(),
            translations = translations ?: mapOf(),
            currencies = currencies?.map { "${it.name}, ${it.symbol}" } ?: emptyList(),
        )
    }

}

