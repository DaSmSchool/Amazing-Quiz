package com.example.amazingquiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(appBar)

        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = prefs.edit()

        if (prefs.getInt("totalAmountAnswered", -1) == -1) {
            prefEditor.putInt("totalAmountAnswered", 0)
        }
        if (prefs.getInt("totalAmountCorrect", -1) == -1) {
            prefEditor.putInt("totalAmountCorrect", 0)
        }
        if (prefs.getInt("totalPasses", -1) == -1) {
            prefEditor.putInt("totalPasses", 0)
        }
        if (prefs.getInt("totalTakes", -1) == -1) {
            prefEditor.putInt("totalTakes", 0)
        }


        prefEditor.apply()

    }
}