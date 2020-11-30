package com.alexandrpershin.country.explorer.extensions

import android.animation.ValueAnimator
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.google.android.material.tabs.TabLayout

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

