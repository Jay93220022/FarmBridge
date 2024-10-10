// LanguageViewModel.kt
package com.example.farmbridge.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.farmbridge.ui.theme.Navigation.PreferenceHelper
import com.example.farmbridge.ui.theme.Repository.LanguageRepository

class LanguageViewModel(
    private val repository: LanguageRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    // Use preference helper to get the current language on initialization
    private val _currentLanguage = MutableStateFlow(preferenceHelper.getLanguage() ?: "English") // Default language
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(language: String) {
        // Update the preference and emit the new language
        preferenceHelper.setLanguage(language) // Save to preferences
        viewModelScope.launch {
            _currentLanguage.emit(language) // Update state flow
        }
    }

    // Method to get supported languages
    fun getSupportedLanguages(): List<String> {
        return repository.getSupportedLanguages()
    }

    // Removed this method as currentLanguage is now handled through the state flow
    // fun getCurrentLanguage(): String? {
    //     return preferenceHelper.getLanguage()
    // }

    // ViewModel Factory for dependency injection
    class Factory(
        private val repository: LanguageRepository,
        private val preferenceHelper: PreferenceHelper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LanguageViewModel(repository, preferenceHelper) as T // Pass preferenceHelper
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
