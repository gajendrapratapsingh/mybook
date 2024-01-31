package com.info.mybook.utils

import android.util.Log
import com.info.mybook.models.LoginRequest
import com.info.mybook.models.LoginResponse
import com.info.mybook.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

class ApiService @Inject constructor() {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun getUsers(): User {
        return client.get(ApiConstants.USERS_ENDPOINT)
    }

    suspend fun loginUsers(username: String, password: String): LoginResponse? {
        val loginRequest = LoginRequest(username = username, password = password)
        //val loginRequest = LoginRequest(username = "kminchelle", password = "0lelplR")
        return try {
            val response: HttpResponse = client.post("https://dummyjson.com/auth/login") {
                contentType(ContentType.Application.Json)
                body = loginRequest
            }
            if(response.status.isSuccess()) {
                return response.receive()
            } else {
                Log.d("API Error", "Failed to log in. Status code: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.d("login exception", e.message.toString())
            null
        }
    }
}