package com.alexandrpershin.country.explorer.api.response

import com.google.gson.annotations.SerializedName

class CurrencyDto(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
)