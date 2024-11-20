package com.example.farmbridge.ui.theme.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmbridge.ui.theme.Repository.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomerAuthViewModel(private val repository: CustomerRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val isAuthenticated = repository.authenticateCustomer(email, password)
                if (isAuthenticated) {
                    _loginState.value = LoginState(isSuccessful = true)
                } else {
                    _loginState.value = LoginState(error = "Invalid credentials or not registered")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState(error = e.message ?: "Unknown error")
            }
        }
    }
}

data class LoginState(
    val isSuccessful: Boolean = false,
    val error: String? = null
)
