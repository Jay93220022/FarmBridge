// LanguageRepository.kt
package com.example.farmbridge.ui.theme.Repository

import com.example.farmbridge.ui.theme.Navigation.PreferenceHelper

class LanguageRepository(private val preferenceHelper: PreferenceHelper) {
    // In a more advanced implementation, this could manage language settings,
    // retrieve them from local storage, remote server, etc.
    fun setLanguage(language: String) {
        preferenceHelper.setLanguage(language)
    }

    fun getLanguage(): String? {
        return preferenceHelper.getLanguage()
    }
    // For now, it's a placeholder for future enhancements.
    fun getSupportedLanguages(): List<String> {
        return listOf("English", "Hindi", "Marathi")
    }
    fun getCurrentLanguage(): String? {
        return preferenceHelper.getLanguage()
    }
}
