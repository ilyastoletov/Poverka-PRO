package com.poverka.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "measurement",
    indices = [Index("checkup_id", unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CheckupEntity::class,
            parentColumns = ["client_id"],
            childColumns = ["checkup_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MeasurementEntity(
    @PrimaryKey
    val number: Int,
    @ColumnInfo("before_pressure_uri") val beforePressureUri: String,
    @ColumnInfo("before_pressure_reading") val beforePressureReading: String,
    @ColumnInfo("after_pressure_uri") val afterPressureUri: String,
    @ColumnInfo("after_pressure_reading") val afterPressureReading: String,
    @ColumnInfo("checkup_id") val checkupId: String,
    val consumption: String,
    @ColumnInfo("reference_consumption")
    val referenceConsumption: String,
    @ColumnInfo("relative_inaccuracy")
    val relativeInaccuracy: String,
    @ColumnInfo("time")
    val measurementTime: String
)
