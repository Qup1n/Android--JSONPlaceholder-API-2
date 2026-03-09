package com.example.jsonpusers.data.repository

import com.example.jsonpusers.data.api.ApiClient
import com.example.jsonpusers.data.dto.UserDto
import com.example.jsonpusers.domain.model.User
import com.example.jsonpusers.domain.repository.UserRepository
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    private var cachedUsers: List<User>? = null

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            delay(1000)
            val response = ApiClient.api.getUsers()
            val users = response.map { it.toDomain() }
            cachedUsers = users
            Result.success(users)
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error: ${e.message}"))
        }
    }

    override suspend fun getUser(id: Int): Result<User> {
        return try {
            delay(800)
            val response = ApiClient.api.getUser(id)
            Result.success(response.toDomain())
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("User not found or error: ${e.message}"))
        }
    }

    private fun UserDto.toDomain(): User {
        return User(
            id = id,
            name = name,
            username = username,
            email = email,
            phone = phone,
            website = website,
            address = "${address.street} ${address.suite}".trim(),
            company = company.name,
            city = address.city,
            fullAddress = "${address.street} ${address.suite}, ${address.city}, ${address.zipcode}"
        )
    }
}