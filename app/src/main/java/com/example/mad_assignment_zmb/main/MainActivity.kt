package com.example.mad_assignment_zmb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.content.Context
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import timber.log.Timber

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        val preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = preferences.getBoolean("dark_mode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.equiFind_linearLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewYardButton =
            findViewById<Button>(R.id.listYardButton)

        val addNewYardButton =
            findViewById<Button>(R.id.addYardButton)

        val settingsButton =
            findViewById<Button>(R.id.settingsButton)

       viewYardButton.setOnClickListener {
            startActivity(
                Intent(this, YardListActivity::class.java)
            )
        }

        addNewYardButton.setOnClickListener {
            startActivity(
                Intent(this, AddEditActivity::class.java)
            )
        }

        settingsButton.setOnClickListener {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }
    }

}