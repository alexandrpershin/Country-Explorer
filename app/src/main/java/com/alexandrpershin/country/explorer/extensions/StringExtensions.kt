package com.alexandrpershin.country.explorer.extensions

fun String?.toSafeString(): String {
    return if (this == null || this == "null") {
        ""
    } else {
        this
    }
}