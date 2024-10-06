// LanguageRepository.kt
package com.example.farmbridge.ui.theme.Repository

class LanguageRepository {
    // In a more advanced implementation, this could manage language settings,
    // retrieve them from local storage, remote server, etc.

    // For now, it's a placeholder for future enhancements.
    fun getSupportedLanguages(): List<String> {
        return listOf("English", "Hindi", "Marathi")
    }
}
