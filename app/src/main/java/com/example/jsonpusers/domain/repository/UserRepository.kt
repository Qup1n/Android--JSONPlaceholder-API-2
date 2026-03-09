package com.example.jsonpusers.domain.repository

import com.example.jsonpusers.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun getUser(id: Int): Result<User>
}