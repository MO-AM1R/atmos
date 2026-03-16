package com.example.atmos.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startTimeMs: Long,
    val endTimeMs: Long? = null,
    val type: String,
    val isEnabled: Boolean = true,
    val isExpired: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)