package com.poverka.domain.feature.checkup.model.client

data class Client(
    val fullName: String,
    val type: ClientType,
    val postalCode: String,
    val city: String,
    val street: String,
    val house: String,
    val flat: String
)
