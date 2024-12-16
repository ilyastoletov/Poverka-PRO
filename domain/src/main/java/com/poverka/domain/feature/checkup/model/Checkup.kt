package com.poverka.domain.feature.checkup.model

import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.meter.MeterInfo

data class Checkup(
    val protocolId: String,
    val date: String,
    val client: Client,
    val meter: MeterInfo,
    val measurements: List<Measurement>
)
