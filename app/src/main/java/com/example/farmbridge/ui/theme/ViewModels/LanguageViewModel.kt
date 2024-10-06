// LanguageViewModel.kt
package com.example.farmbridge.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.farmbridge.ui.theme.Repository.LanguageRepository

class LanguageViewModel(private val repository: LanguageRepository) : ViewModel() {
    private val _currentLanguage = MutableStateFlow("English") // Default language
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(language: String) {
        viewModelScope.launch {
            _currentLanguage.emit(language)
        }
    }

    // Method to get supported languages
    fun getSupportedLanguages(): List<String> {
        return repository.getSupportedLanguages()
    }

    class Factory(private val repository: LanguageRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LanguageViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
