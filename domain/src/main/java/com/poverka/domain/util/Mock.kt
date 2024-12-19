package com.poverka.domain.util

import com.poverka.domain.feature.checkup.model.Checkup
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.client.ClientType
import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.domain.feature.checkup.model.meter.MeterPosition
import com.poverka.domain.feature.checkup.model.meter.VisualInspection
import com.poverka.domain.feature.checkup.model.meter.WaterSupply

object Mock {

    val demoClient = Client(
        fullName = "Островский Владимир Викторович",
        type = ClientType.PERSON,
        postalCode = "445022",
        city = "Тольятти",
        street = "Автостроителей",
        house = "22",
        flat = "12"
    )

    val demoMeter = MeterInfo(
        protocolId = null,
        registrationId = "0098881",
        waterSupply = WaterSupply.HOT,
        releaseYear = "2012",
        modification = null,
        factoryNumber = "99-499991",
        meterPosition = MeterPosition.HORIZONTAL,
        visualInspection = VisualInspection.CORRECT
    )

    val demoMeasurements = listOf(
        Measurement(
            consumption = "0,004",
            referenceConsumption = "0,0041",
            relativeInaccuracy = "0,01",
            measurementTime = "360"
        ),
        Measurement(
            consumption = "0,005",
            referenceConsumption = "0,00999541",
            relativeInaccuracy = "4",
            measurementTime = "480"
        ),
        Measurement(
            consumption = "0,009581",
            referenceConsumption = "0,000421",
            relativeInaccuracy = "5,661",
            measurementTime = "720"
        ),
    )

    val demoCheckup = Checkup(
        protocolId = "45-0001",
        date = "12.12.2024",
        client = demoClient,
        meter = demoMeter,
        measurements = demoMeasurements
    )

    val demoCheckups = listOf(
        demoCheckup,
        Checkup(
            protocolId = "45-0041",
            date = "11.12.2024",
            client = demoClient,
            meter = demoMeter,
            measurements = demoMeasurements
        ),
        Checkup(
            protocolId = "45-0051",
            date = "10.12.2024",
            client = demoClient,
            meter = demoMeter,
            measurements = demoMeasurements
        ),
    )


}