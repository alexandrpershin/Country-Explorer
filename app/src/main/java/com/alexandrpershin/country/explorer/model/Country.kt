package com.alexandrpershin.country.explorer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey val name: String,
    val nativeName: String,
    val flag: String,
    val capital: String,
    val countryCode: String,
    val phoneCode: String,
    val population: Int,
    val subregion: String,
    val languages: List<String>,
    val borders: List<String>,
    val currencies: List<String>
) {

    override fun toString(): String {
        return "Country(name='$name')"
    }
}