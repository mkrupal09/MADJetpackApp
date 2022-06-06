package com.example.mycomposecookbook.screen.home

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mycomposecookbook.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val usersFlow: Flow<PagingData<User>> = Pager(PagingConfig(pageSize = 6)) {
        UserSource(userRepository) {
            userListLoading.tryEmit(it)
        }
    }.flow

    val userListLoading = MutableSharedFlow<Boolean>(0)

    fun fetchUsers() {
        /*viewModelScope.launch {
            val users = userRepository.getUsers()
            withContext(Dispatchers.Main)
            {
                usersFlow.emit(users)
            }
        }*/
    }

    suspend fun coroutines(): String {
        return withContext(Dispatchers.IO) {
            ""
        }
    }

}