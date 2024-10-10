package com.example.farmbridge.ui.theme.Navigation

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val LANGUAGE_KEY = "selected_language"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)

    // Save login status
    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", isLoggedIn)
        editor.apply()
    }

    // Retrieve login status
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    // Clear login status (for logout)
    fun clearLogin() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun setLanguage(language: String) {
        sharedPreferences.edit().putString(LANGUAGE_KEY, language).apply()
    }

    // Function to get the selected language
    fun getLanguage(): String? {
        return sharedPreferences.getString(LANGUAGE_KEY, null)
    }
}
