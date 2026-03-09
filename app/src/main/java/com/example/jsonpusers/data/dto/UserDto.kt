package com.example.jsonpusers.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: AddressDto,
    val company: CompanyDto
)

data class AddressDto(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoDto
)

data class GeoDto(
    val lat: String,
    val lng: String
)

data class CompanyDto(
    val name: String,
    val catchPhrase: String,
    val bs: String
)