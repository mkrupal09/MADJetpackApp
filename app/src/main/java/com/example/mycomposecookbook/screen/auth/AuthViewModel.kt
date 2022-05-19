package com.example.mycomposecookbook.screen.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel : ViewModel() {

    val emailError = MutableStateFlow("")
    val passwordError = MutableStateFlow("")
    val navigateHome = MutableStateFlow(false)

    fun login(email: String, password: String) {

        emailError.value = if (email.isEmpty()) "Please enter email" else ""
        passwordError.value = if (password.isEmpty()) "Please enter password" else ""

        if (emailError.value.isEmpty() && passwordError.value.isEmpty()) {
            navigateHome.value = true
        }
    }
}