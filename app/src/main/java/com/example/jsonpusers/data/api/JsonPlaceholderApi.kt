package com.example.jsonpusers.data.api

import com.example.jsonpusers.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDto
}