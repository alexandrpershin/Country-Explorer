package com.alexandrpershin.country.explorer.api.response

import com.alexandrpershin.country.explorer.api.mapper.Mapper
import com.alexandrpershin.country.explorer.extensions.toSafeString
import com.alexandrpershin.country.explorer.model.Country
import com.google.gson.annotations.SerializedName


/**
 * Pojo response from backend
 * */

class CountryResponse(
    @SerializedName("alpha3Code") val alphaCode: String?,
    @SerializedName("borders") val borders: List<String>?,
    @SerializedName("callingCodes") val callingCodes: List<String>?,
    @SerializedName("capital") val capital: String?,
    @SerializedName("currencies") val currencies: List<CurrencyDto>?,
    @SerializedName("flag") val flag: String?,
    @SerializedName("languages") val languages: List<LanguageDto>?,
    @SerializedName("name") val name: String?,
    @SerializedName("nativeName") val nativeName: String?,
    @SerializedName("population") val population: Int?,
    @SerializedName("subregion") val subRegion: String?,
    @SerializedName("translations") val translations: Map<String, String>?
) : Mapper<Country> {

    /**
     * Convert response object from DTO (Data transfer object) to entity object
     * */

    override fun toEntity(): Country {
        return Country(
            name = name.toSafeString(),
            nativeName = nativeName.toSafeString(),
            flag = flag.toSafeString(),
            capital = capital.toSafeString(),
            phoneCode = callingCodes?.joinToString().toSafeString(),
            countryCode = alphaCode.toSafeString(),
            population = population ?: 0,
            subRegion = subRegion.toSafeString(),
            languages = languages?.map { it.name.toSafeString() } ?: emptyList(),
            borders = borders ?: emptyList(),
            translations = translations ?: mapOf(),
            currencies = currencies?.map { "${it.name}, ${it.symbol}" } ?: emptyList(),
        )
    }

}

