package com.alexandrpershin.country.explorer.utils

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToWithNavDirection(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
}