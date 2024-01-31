package com.info.mybook.repositories

import com.info.mybook.models.LoginResponse
import com.info.mybook.models.User
import com.info.mybook.models.Users
import com.info.mybook.utils.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUsers(): List<Users> {
        return apiService.getUsers().users
    }

    suspend fun loginUsers(username : String, password : String): LoginResponse? {
        return apiService.loginUsers(username, password)
    }
}
