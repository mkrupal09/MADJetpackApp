package com.example.mycomposecookbook.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.mycomposecookbook.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val usersFlow: Flow<PagingData<User>> = Pager(PagingConfig(pageSize = 6)) {
        UserSource(userRepository) {
            Log.e("Loadingcallback", it.toString())

            userListLoading.tryEmit(it)

        }
    }.flow


    val userListLoading = MutableStateFlow(false)
    var searchKeyword: String = ""


    fun fetchUsers() {

        /*viewModelScope.launch {
     g       val users = userRepository.getUsers()
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