package com.example.taskmanager.ui.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "themePref"
    private val KEY_THEME = "isDarkMode"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean(KEY_THEME, false)
        binding.switchTheme.isChecked = isDarkMode

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            sharedPreferences.edit().putBoolean(KEY_THEME, isChecked).apply()
        }

        return binding.root
    }
}