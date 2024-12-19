package com.poverka.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.poverka.data.storage.entity.CheckupEntity
import com.poverka.data.storage.entity.ClientEntity
import com.poverka.data.storage.entity.MeasurementEntity
import com.poverka.data.storage.entity.MeterEntity
import com.poverka.domain.feature.checkup.model.CheckupStage

@Dao
interface CheckupDao {

    @Insert(entity = ClientEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(entity: ClientEntity)

    @Insert(entity = MeterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeter(meterEntity: MeterEntity)

    @Insert(entity = MeasurementEntity::class)
    suspend fun insertMeasurement(measurementEntity: MeasurementEntity)

    @Insert(entity = CheckupEntity::class)
    suspend fun insertCheckup(checkupEntity: CheckupEntity)

    @Query("UPDATE checkup SET stage = :stage")
    suspend fun updateStage(stage: CheckupStage)

    @Query("SELECT * FROM checkup LIMIT 1")
    suspend fun getCheckup(): CheckupEntity?

    @Query("SELECT * FROM client LIMIT 1")
    suspend fun getClient(): ClientEntity?

    @Query("SELECT * FROM meter LIMIT 1")
    suspend fun getMeter(): MeterEntity?

    @Query("SELECT * FROM measurement")
    suspend fun getAllMeasurements(): List<MeasurementEntity>

    @Transaction
    suspend fun createCheckupIfNotExist() {
        if (getCheckup() == null) {
            val defaultCheckup = CheckupEntity(1, 1, CheckupStage.CLIENT)
            insertCheckup(defaultCheckup)
        }
    }

    @Transaction
    suspend fun createClient(clientEntity: ClientEntity) {
        insertClient(clientEntity)
        createCheckupIfNotExist()
        updateStage(CheckupStage.METER)
    }

    @Transaction
    suspend fun createMeter(meterEntity: MeterEntity) {
        insertMeter(meterEntity)
        updateStage(CheckupStage.FIRST_MEASUREMENT)
    }

    @Transaction
    suspend fun createMeasurement(measurementEntity: MeasurementEntity) {
        insertMeasurement(measurementEntity)
        val latestMeasurement = getAllMeasurements().last()
        updateStage(
            when(latestMeasurement.number) {
                1 -> CheckupStage.SECOND_MEASUREMENT
                2 -> CheckupStage.THIRD_MEASUREMENT
                else -> CheckupStage.CLIENT
            }
        )
    }

    @Query("DELETE FROM checkup")
    suspend fun clear()

}