package com.example.atmos.ui.favoritedetails.state

import com.example.atmos.domain.model.StoredPoint

sealed class FavoriteDetailsEvent {
    data class OnLoad(
        val point: StoredPoint,
        val forceUpdate: Boolean = false
    ) : FavoriteDetailsEvent()
}