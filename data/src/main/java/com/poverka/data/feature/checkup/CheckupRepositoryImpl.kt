package com.poverka.data.feature.checkup

import com.poverka.domain.feature.checkup.model.CheckupStage
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.domain.feature.checkup.model.MeterTestResults
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.domain.feature.checkup.repository.CheckupRepository

class CheckupRepositoryImpl : CheckupRepository {

    override suspend fun getCurrentCheckupStage(): CheckupStage {
        TODO("Not yet implemented")
    }

    override suspend fun uploadClientData(data: Client): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun loadLatestProtocols(): Result<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getClientByProtocol(protocolId: String): Result<Client> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentClientData(): Result<Client> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadMeterData(data: MeterInfo): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentMeterData(): Result<MeterInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun calculateMeasurementDetails(testResults: MeterTestResults): Result<Measurement> {
        TODO("Not yet implemented")
    }

}