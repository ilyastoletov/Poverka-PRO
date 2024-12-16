package com.poverka.domain.feature.checkup.model

data class Measurement(
    val consumption: String,
    val referenceConsumption: String,
    val relativeInaccuracy: String,
    val measurementTime: String
)
