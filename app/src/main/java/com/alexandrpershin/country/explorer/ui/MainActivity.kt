package com.alexandrpershin.country.explorer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexandrpershin.country.explorer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
    }
}