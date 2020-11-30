package com.alexandrpershin.country.explorer.extensions

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * View extensions
 * */


fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

/**
 * SwipeRefreshLayout extensions
 * */

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

