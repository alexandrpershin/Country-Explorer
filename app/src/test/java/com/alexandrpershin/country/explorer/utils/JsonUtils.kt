package com.alexandrpershin.country.explorer.utils

import com.alexandrpershin.country.explorer.api.response.CountryResponse
import com.alexandrpershin.country.explorer.model.Country
import com.google.gson.Gson

object JsonUtils {
    val gson = Gson()

    fun provideTestCountry(): Country {
        val response = provideTestCountryResponse()
        return response.toEntity()
    }

    fun provideTestCountryResponse(): CountryResponse {
        return gson.fromJson(countryResponse, CountryResponse::class.java)
    }

    private val countryResponse = "{\n" +
            "    \"name\": \"Italy\",\n" +
            "    \"topLevelDomain\": [\n" +
            "      \".it\"\n" +
            "    ],\n" +
            "    \"alpha2Code\": \"IT\",\n" +
            "    \"alpha3Code\": \"ITA\",\n" +
            "    \"callingCodes\": [\n" +
            "      \"39\"\n" +
            "    ],\n" +
            "    \"capital\": \"Rome\",\n" +
            "    \"altSpellings\": [\n" +
            "      \"IT\",\n" +
            "      \"Italian Republic\",\n" +
            "      \"Repubblica italiana\"\n" +
            "    ],\n" +
            "    \"region\": \"Europe\",\n" +
            "    \"subregion\": \"Southern Europe\",\n" +
            "    \"population\": 60665551,\n" +
            "    \"latlng\": [\n" +
            "      42.83333333,\n" +
            "      12.83333333\n" +
            "    ],\n" +
            "    \"demonym\": \"Italian\",\n" +
            "    \"area\": 301336,\n" +
            "    \"gini\": 36,\n" +
            "    \"timezones\": [\n" +
            "      \"UTC+01:00\"\n" +
            "    ],\n" +
            "    \"borders\": [\n" +
            "      \"AUT\",\n" +
            "      \"FRA\",\n" +
            "      \"SMR\",\n" +
            "      \"SVN\",\n" +
            "      \"CHE\",\n" +
            "      \"VAT\"\n" +
            "    ],\n" +
            "    \"nativeName\": \"Italia\",\n" +
            "    \"numericCode\": \"380\",\n" +
            "    \"currencies\": [\n" +
            "      {\n" +
            "        \"code\": \"EUR\",\n" +
            "        \"name\": \"Euro\",\n" +
            "        \"symbol\": \"€\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"languages\": [\n" +
            "      {\n" +
            "        \"iso639_1\": \"it\",\n" +
            "        \"iso639_2\": \"ita\",\n" +
            "        \"name\": \"Italian\",\n" +
            "        \"nativeName\": \"Italiano\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"translations\": {\n" +
            "      \"de\": \"Italien\",\n" +
            "      \"es\": \"Italia\",\n" +
            "      \"fr\": \"Italie\",\n" +
            "      \"ja\": \"イタリア\",\n" +
            "      \"it\": \"Italia\",\n" +
            "      \"br\": \"Itália\",\n" +
            "      \"pt\": \"Itália\",\n" +
            "      \"nl\": \"Italië\",\n" +
            "      \"hr\": \"Italija\",\n" +
            "      \"fa\": \"ایتالیا\"\n" +
            "    },\n" +
            "    \"flag\": \"https://restcountries.eu/data/ita.svg\",\n" +
            "    \"regionalBlocs\": [\n" +
            "      {\n" +
            "        \"acronym\": \"EU\",\n" +
            "        \"name\": \"European Union\",\n" +
            "        \"otherAcronyms\": [],\n" +
            "        \"otherNames\": []\n" +
            "      }\n" +
            "    ],\n" +
            "    \"cioc\": \"ITA\"\n" +
            "  }"

}


