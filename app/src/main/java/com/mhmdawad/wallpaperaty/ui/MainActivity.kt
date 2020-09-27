package com.mhmdawad.wallpaperaty.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.utils.getLastThemeMode
import dagger.hilt.android.AndroidEntryPoint


const val NIGHT_MODE = "night_mode"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
       changeTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun changeTheme(){
        val isLight = getLastThemeMode()
        if (isLight) {
            setTheme(R.style.AppThemeLight)
        } else {
            setTheme(R.style.AppThemeDark)
        }
    }
}
















