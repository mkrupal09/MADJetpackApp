package com.example.mycomposecookbook.screen.auth

import androidx.lifecycle.viewModelScope
import com.example.mycomposecookbook.screen.home.UserRepository
import com.example.mycomposecookbook.util.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    val emailError = MutableStateFlow("")
    val passwordError = MutableStateFlow("")
    val navigateHome = MutableSharedFlow<Boolean>(0)

    fun login(email: String, password: String) {

        emailError.value = if (email.isEmpty()) "Please enter email" else ""
        passwordError.value = if (password.isEmpty()) "Please enter password" else ""

        if (emailError.value.isEmpty() && passwordError.value.isEmpty()) {
            showLoading(true)
            viewModelScope.launch {
                val value = userRepository.login(email, password)
                showLoading(false)
                navigateHome.emit(value)
            }
        }
    }
}