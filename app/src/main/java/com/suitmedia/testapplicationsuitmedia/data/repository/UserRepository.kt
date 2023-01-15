package com.suitmedia.testapplicationsuitmedia.data.repository

import com.suitmedia.testapplicationsuitmedia.data.network.response.ListUsersResponse
import com.suitmedia.testapplicationsuitmedia.data.network.service.ApiService
import com.suitmedia.testapplicationsuitmedia.wrapper.Resource

class UserRepository(
    private val apiService : ApiService
) {

    suspend fun getListUsers(page: Int): Resource<ListUsersResponse> = proceed {
        apiService.getListUsers(page)
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}