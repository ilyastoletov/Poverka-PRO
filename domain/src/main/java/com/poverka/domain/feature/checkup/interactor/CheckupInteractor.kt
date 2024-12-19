package com.poverka.domain.feature.checkup.interactor

import com.poverka.domain.feature.checkup.model.MeterTestResults
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.domain.feature.checkup.repository.CheckupRepository

class CheckupInteractor(private val repository: CheckupRepository) {

    suspend fun getCurrentCheckupStage() =
        repository.getCurrentCheckupStage()

    suspend fun uploadClientData(data: Client) =
        repository.uploadClientData(data)

    suspend fun loadLatestProtocols() =
        repository.loadLatestProtocols()

    suspend fun getClientByProtocol(protocolId: String) =
        repository.getClientByProtocol(protocolId)

    suspend fun getCurrentClientData() =
        repository.getCurrentClientData()

    suspend fun uploadMeterData(data: MeterInfo) =
        repository.uploadMeterData(data)

    suspend fun getCurrentMeterData() =
        repository.getCurrentMeterData()

    suspend fun calculateMeasurementDetails(testResults: MeterTestResults) =
        repository.calculateMeasurementDetails(testResults)

}