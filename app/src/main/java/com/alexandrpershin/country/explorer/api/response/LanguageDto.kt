package com.alexandrpershin.country.explorer.api.response

import com.google.gson.annotations.SerializedName

class LanguageDto(
    @SerializedName("name") val name: String?,
    @SerializedName("nativeName") val nativeName: String?
)