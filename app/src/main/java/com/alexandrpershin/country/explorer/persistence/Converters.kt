package com.alexandrpershin.country.explorer.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * This class converts from a unknown type into a known type in terms of database types
 * */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun getStringFromList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun getListFromString(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {
        }.type
        return Gson().fromJson<List<String>>(json, listType)
    }

    @TypeConverter
    fun getMapFromString(json: String): Map<String, String> {
        val listType = object : TypeToken<Map<String, String>>() {
        }.type
        return Gson().fromJson<Map<String, String>>(json, listType)
    }

    @TypeConverter
    fun getStringFromMap(map: Map<String, String>): String {
        return gson.toJson(map)
    }


}
