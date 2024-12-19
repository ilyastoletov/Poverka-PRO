package com.poverka.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poverka.data.storage.dao.CheckupDao
import com.poverka.data.storage.entity.CheckupEntity
import com.poverka.data.storage.entity.ClientEntity
import com.poverka.data.storage.entity.MeasurementEntity
import com.poverka.data.storage.entity.MeterEntity

@Database(
    entities = [
        CheckupEntity::class,
        ClientEntity::class,
        MeasurementEntity::class,
        MeterEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CheckupDatabase : RoomDatabase() {

    abstract fun getCheckupDao(): CheckupDao

}