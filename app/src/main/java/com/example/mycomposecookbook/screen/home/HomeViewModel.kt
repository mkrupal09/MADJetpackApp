package com.example.mycomposecookbook.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposecookbook.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val usersFlow = MutableStateFlow<List<User>>(arrayListOf())

    fun fetchUsers() {
        viewModelScope.launch {
            val users = userRepository.getUsers()
            withContext(Dispatchers.Main)
            {
                usersFlow.tryEmit(users)
            }
        }
    }

}