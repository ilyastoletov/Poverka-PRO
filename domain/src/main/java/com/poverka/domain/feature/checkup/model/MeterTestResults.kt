package com.poverka.domain.feature.checkup.model

import java.io.File

data class MeterTestResults(
    val beforePressurePhoto: File,
    val beforePressureReadings: String,
    val afterPressurePhoto: File,
    val afterPressureReadings: File
)
