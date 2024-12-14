package com.poverka.pro.presentation.feature.verificationTools.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
sealed class Stage(
    val number: Int,
    val tool: String,
    val title: String,
    val next: Stage?
) : Parcelable {

    data object Reference : Stage(
        number = 1,
        tool = "Эталон",
        title = "Сделайте фото подключенного эталона",
        next = Thermohygrometer
    )

    data object Thermohygrometer : Stage(
        number = 2,
        tool = "Термогигрометр",
        title = "Сделайте фото термогигрометра",
        next = Thermometer
    )

    data object Thermometer : Stage(
        number = 3,
        tool = "Термометр",
        title = "Сделайте фото термометра",
        next = Stopwatch
    )

    data object Stopwatch : Stage(
        number = 4,
        tool = "Секундомер",
        title = "Сделайте фото секундомера",
        next = null
    )

}