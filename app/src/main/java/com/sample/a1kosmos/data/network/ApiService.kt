package com.sample.a1kosmos.data.network

import com.sample.a1kosmos.data.model.user.UserData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/users/")
    suspend fun getUserData(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 10,
    ): UserData
}