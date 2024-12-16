package com.poverka.domain.feature.checkup.model.meter

data class MeterInfo(
    val registrationId: String,
    val waterSupply: WaterSupply,
    val releaseYear: String,
    val modification: String?,
    val factoryNumber: String,
    val meterPosition: MeterPosition,
    val visualInspection: VisualInspection
)
