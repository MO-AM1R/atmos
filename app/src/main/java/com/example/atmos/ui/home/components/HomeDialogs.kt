package com.example.atmos.ui.home.components

import androidx.compose.runtime.Composable
import com.example.atmos.ui.core.components.PermanentDenyDialog
import com.example.atmos.ui.core.components.PermissionRationaleDialog


@Composable
fun HomeDialogs(
    showPermissionRationaleDialog: Boolean,
    showPermanentDenyDialog: Boolean,
    onRationaleConfirm: () -> Unit,
    onRationaleDismiss: () -> Unit,
    onPermanentConfirm: () -> Unit,
    onPermanentDismiss: () -> Unit,
) {
    if (showPermissionRationaleDialog) {
        PermissionRationaleDialog(
            onConfirm = onRationaleConfirm,
            onDismiss = onRationaleDismiss
        )
    }

    if (showPermanentDenyDialog) {
        PermanentDenyDialog(
            onOpenSettings = onPermanentConfirm,
            onDismiss = onPermanentDismiss
        )
    }
}