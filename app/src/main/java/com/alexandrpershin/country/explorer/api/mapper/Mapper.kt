package com.alexandrpershin.country.explorer.api.mapper

interface Mapper<T> {

    fun toEntity(): T

}