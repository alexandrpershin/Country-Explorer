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
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.google.android.material.tabs.TabLayout

/**
 * View extensions
 * */


fun View.changeBackgroundWithAnimation(colorFrom: Int, colorTo: Int) {
    val colorAnimation: ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = 200L

    colorAnimation.addUpdateListener { animator ->
        setBackgroundColor(animator.animatedValue as Int)
        requestLayout()
    }
    colorAnimation.start()
}

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


fun View.disableClick() {
    isFocusable = false
    isEnabled = false
    isClickable = false
}

fun View.enableClick() {
    isFocusable = true
    isEnabled = true
    isClickable = true
}


/**
 * MenuItem extensions
 * */

fun MenuItem?.makeGone() {
    this?.isVisible = false
}

/**
 * ViewGroup extensions
 * */

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/**
 * EditText extensions
 * */

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun TabLayout.onTabChanged(callback: (Int) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            callback(tab!!.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}

    })
}

