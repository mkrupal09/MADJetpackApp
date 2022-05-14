package com.example.mycomposecookbook.screen.home

import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getUsers(): List<User>
}


class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        apiService.fetchUsers().body()?.list!!
    }
}


