package com.poverka.domain.feature.checkup.repository

import com.poverka.domain.feature.checkup.model.CheckupStage
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.domain.feature.checkup.model.MeterTestResults
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.meter.MeterInfo

interface CheckupRepository {
    suspend fun getCurrentCheckupStage(): CheckupStage
    suspend fun uploadClientData(data: Client): Result<Unit>
    suspend fun loadLatestProtocols(): Result<List<String>>
    suspend fun getClientByProtocol(protocolId: String): Result<Client>
    suspend fun getCurrentClientData(): Result<Client>
    suspend fun uploadMeterData(data: MeterInfo): Result<Unit>
    suspend fun getCurrentMeterData(): Result<MeterInfo>
    suspend fun calculateMeasurementDetails(testResults: MeterTestResults): Result<Measurement>
}