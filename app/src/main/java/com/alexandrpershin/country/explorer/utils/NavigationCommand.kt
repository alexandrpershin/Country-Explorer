package com.alexandrpershin.country.explorer.utils

import android.os.Bundle
import androidx.navigation.NavDirections
import java.io.Serializable

sealed class NavigationCommand {
    data class ToWithId(val directions: Int) : NavigationCommand()
    data class ToWithNavDirection(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackResult(val resultKey: String, val result: Serializable) : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    data class WithArgs(
        val destinationId: Int,
        val args: Bundle
    ) : NavigationCommand()
}