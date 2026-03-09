package com.example.jsonpusers.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: String,
    val company: String,
    val city: String,
    val fullAddress: String
)