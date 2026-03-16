package com.example.atmos.data.mappers

import com.example.atmos.data.database.entity.AlertEntity
import com.example.atmos.data.enums.AlertType
import com.example.atmos.domain.model.Alert

fun AlertEntity.toDomain() = Alert(
    id = id,
    startTimeMs = startTimeMs,
    endTimeMs = endTimeMs,
    type = AlertType.valueOf(type),
    isEnabled = isEnabled,
    isExpired = isExpired
)

fun Alert.toEntity() = AlertEntity(
    id = id,
    startTimeMs = startTimeMs,
    endTimeMs = endTimeMs,
    type = type.name,
    isEnabled = isEnabled,
    isExpired = isExpired
)