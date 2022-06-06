package com.example.mycomposecookbook.data.remote

import com.example.mycomposecookbook.data.model.ListResponse
import com.example.mycomposecookbook.data.model.User
import retrofit2.Response
import retrofit2.http.*

const val BASE_URL = "https://reqres.in"

interface ApiService {

    @POST("api/users")
    @FormUrlEncoded
    suspend fun createUser(@Field("name") name: String, @Field("job") job: String)


    @GET("api/users")
    suspend fun fetchUsers(@Query("page") page: Int = 1): Response<ListResponse<User>>
}