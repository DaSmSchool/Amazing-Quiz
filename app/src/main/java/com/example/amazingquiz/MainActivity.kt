package com.example.amazingquiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(appBar)
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph: NavGraph = navController.graph // get the navigation graph associated with the controller
        appBarConfig = AppBarConfiguration(navGraph) // create an app configuration based on the nav graph
        setupActionBarWithNavController(navController, appBarConfig) // enable navigation support in the app bar

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

    override fun onSupportNavigateUp(): Boolean {
        val success: Boolean = navController.navigateUp(appBarConfig)
        return success || super.onSupportNavigateUp()
    }


}