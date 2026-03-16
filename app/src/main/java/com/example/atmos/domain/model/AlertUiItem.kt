package com.example.atmos.domain.model

import com.example.atmos.data.enums.AlertType

data class AlertUiItem(
    val id: Int,
    val startTimeMs: Long,
    val endTimeMs: Long?,
    val type: AlertType,
    val isEnabled: Boolean,
    val isExpired: Boolean
)

fun Alert.toUiItem() = AlertUiItem(
    id = id,
    startTimeMs = startTimeMs,
    endTimeMs = endTimeMs,
    type = type,
    isEnabled = isEnabled,
    isExpired = isExpired
)