package com.poverka.domain.util

import com.poverka.domain.feature.home.Checkup

object Mock {

    val testCheckups = listOf(
        Checkup(date = "22.10.2024", protocolId = "1-000491"),
        Checkup(date = "15.11.2023", protocolId = "599-0000005122"),
        Checkup(date = "10.12.2024", protocolId = "51-000512"),
    )

}