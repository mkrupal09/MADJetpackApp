package com.example.mycomposecookbook.screen.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycomposecookbook.data.model.ListResponse
import com.example.mycomposecookbook.data.model.User
import com.example.mycomposecookbook.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response

interface UserRepository {
    suspend fun login(email: String, password: String): Boolean

    suspend fun getUsers(page: Int): Response<ListResponse<User>>
}


class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(page: Int): Response<ListResponse<User>> =
        withContext(Dispatchers.IO) {
            apiService.fetchUsers(page)
        }
    override suspend fun login(email: String, password: String): Boolean {
        return apiService.login(email, password).code() == 200
    }
}


class UserSource(private val userRepository: UserRepository, val loadCallback: (Boolean) -> Unit) :
    PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        Log.e("paging", "onrefreshkey")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        loadCallback(true)
        Log.e("paging", "onload " + params.key)
        val position = params.key ?: 1
        val response = userRepository.getUsers(position)
        delay(5000)
        loadCallback(false)
        return LoadResult.Page(
            response.body()?.list ?: arrayListOf(),
            prevKey = if (position <= 1) null else position - 1,
            nextKey = if (response.body()?.page!! < response.body()?.totalPage!!) position + 1 else null
        )
    }

}

