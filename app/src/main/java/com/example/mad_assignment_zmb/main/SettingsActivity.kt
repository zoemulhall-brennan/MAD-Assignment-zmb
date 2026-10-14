package com.example.mad_assignment_zmb

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lightAndDarkModeSwitch = findViewById<Switch>(R.id.light_and_dark_mode_switch)
        val backButton = findViewById<Button>(R.id.back_button)

        // Read saved preference
        lightAndDarkModeSwitch.isChecked = preferences.getBoolean("dark_mode", false)

        // Light / Dark mode toggle
        lightAndDarkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit().putBoolean("dark_mode", isChecked).apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Back button closes SettingsActivity and returns to MainActivity
        backButton.setOnClickListener {
            finish()
        }
    }
}