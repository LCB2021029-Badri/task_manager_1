package com.example.taskmanager

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.taskmanager.ui.list.HelpFragment
import com.example.taskmanager.ui.list.TaskFragment

class MainActivity : AppCompatActivity() {

    // check which theme of app in shared preference
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "themePref"
    private val KEY_THEME = "isDarkMode"

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean(KEY_THEME, false)

        // Set the theme based on the shared preference
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState) // Call this after setting the theme
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            loadFragment(TaskFragment())
        }
    }

    // Function to handle navigation without backstack
    private fun loadFragment(fragment: Fragment) {
        // Replace the current fragment with the new fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                navigateToFragment(HelpFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to handle navigation with backstack
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}