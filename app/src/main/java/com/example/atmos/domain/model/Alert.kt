package com.example.atmos.domain.model

import com.example.atmos.data.enums.AlertType

data class Alert(
    val id: Int,
    val startTimeMs: Long,
    val endTimeMs: Long?,
    val type: AlertType,
    val isEnabled: Boolean,
    val isExpired: Boolean
)