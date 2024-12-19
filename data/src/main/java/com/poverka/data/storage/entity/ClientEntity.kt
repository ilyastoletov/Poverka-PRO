package com.poverka.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.poverka.domain.feature.checkup.model.client.ClientType

@Entity(
    tableName = "client",
    foreignKeys = [
        ForeignKey(
            entity = CheckupEntity::class,
            parentColumns = ["client_id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class ClientEntity(
    @PrimaryKey
    val id: Int = 1,
    @ColumnInfo("full_name")
    val fullName: String,
    val type: ClientType,
    @ColumnInfo("postal_code")
    val postalCode: String,
    val city: String,
    val street: String,
    val house: String,
    val flat: String
)
