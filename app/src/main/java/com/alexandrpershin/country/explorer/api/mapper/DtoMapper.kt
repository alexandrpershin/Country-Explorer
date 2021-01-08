package com.alexandrpershin.country.explorer.api.mapper

/**
 * Base mapper interface. Provides abstract method [toEntity] to parse DTO to entity object
* */

interface DtoMapper<T> {

    fun toEntity(): T
}