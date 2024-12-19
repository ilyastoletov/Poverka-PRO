package com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement.model

import android.net.Uri

data class PhotoWithReadings(
    val photoUri: Uri,
    val readings: String
)
