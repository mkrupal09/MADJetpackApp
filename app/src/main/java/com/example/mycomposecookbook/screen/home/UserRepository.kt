package com.example.mycomposecookbook.screen.home

import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getUsers(): ArrayList<User>

    suspend fun login(email: String, password: String): Boolean
}


class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): ArrayList<User> = withContext(Dispatchers.IO) {
        apiService.fetchUsers().body()?.list!!
    }

    override suspend fun login(email: String, password: String): Boolean {
        return apiService.login(email, password).code() == 200
    }
}


