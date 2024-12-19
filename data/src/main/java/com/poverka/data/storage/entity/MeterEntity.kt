package com.poverka.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.poverka.domain.feature.checkup.model.meter.MeterPosition
import com.poverka.domain.feature.checkup.model.meter.VisualInspection
import com.poverka.domain.feature.checkup.model.meter.WaterSupply

@Entity(
    tableName = "meter",
    foreignKeys = [
        ForeignKey(
            entity = CheckupEntity::class,
            parentColumns = ["meter_id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class MeterEntity(
    @PrimaryKey
    val id: Int = 1,
    @ColumnInfo("protocol_id") val protocolId: String,
    @ColumnInfo("registration_id") val registrationId: String,
    @ColumnInfo("water_supply") val waterSupply: WaterSupply,
    @ColumnInfo("release_year") val releaseYear: String,
    val modification: String?,
    @ColumnInfo("factory_number") val factoryNumber: String,
    @ColumnInfo("meter_position") val meterPosition: MeterPosition,
    @ColumnInfo("visual_inspection") val visualInspection: VisualInspection
)
