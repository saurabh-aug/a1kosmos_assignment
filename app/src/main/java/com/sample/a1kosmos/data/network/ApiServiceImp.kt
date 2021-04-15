package com.sample.a1kosmos.data.network

import com.sample.a1kosmos.data.network.ApiService
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {

    suspend fun getUserData(page: Int, per_page: Int) =
        apiService.getUserData(page, per_page)

}