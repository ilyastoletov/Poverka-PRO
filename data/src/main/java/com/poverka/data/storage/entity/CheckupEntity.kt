package com.poverka.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.poverka.domain.feature.checkup.model.CheckupStage

@Entity(
    tableName = "checkup",
    indices = [
        Index("client_id", unique = true),
        Index("meter_id", unique = true)
    ]
)
data class CheckupEntity(
    @PrimaryKey
    @ColumnInfo(name = "client_id")
    val clientId: Int,

    @ColumnInfo(name = "meter_id")
    val meterId: Int,

    val stage: CheckupStage
)
