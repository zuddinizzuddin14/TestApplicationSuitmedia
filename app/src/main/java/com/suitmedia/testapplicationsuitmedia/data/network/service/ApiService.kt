package com.suitmedia.testapplicationsuitmedia.data.network.service

import com.suitmedia.testapplicationsuitmedia.data.network.response.ListUsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getListUsers(
        @Query("page", encoded = true) page: Int,
    ) : ListUsersResponse

}