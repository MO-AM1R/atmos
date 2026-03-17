package com.example.atmos.ui.alert

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.ui.alert.components.AlertItem
import com.example.atmos.ui.alert.components.AlertItemShimmer
import com.example.atmos.ui.alert.components.AlertsEmptyContent
import com.example.atmos.ui.alert.components.AlertsHeader
import com.example.atmos.ui.alert.components.AlertsSnackbar
import com.example.atmos.ui.alert.components.CreateAlertBottomSheet
import com.example.atmos.ui.alert.components.NotificationPermanentDenyDialog
import com.example.atmos.ui.alert.components.NotificationRationaleDialog
import com.example.atmos.ui.alert.state.AlertsEvent
import com.example.atmos.ui.alert.state.AlertsState
import com.example.atmos.ui.alert.viewmodel.AlertsViewModel
import com.example.atmos.ui.core.viewmodel.NotificationPermissionEvent
import com.example.atmos.ui.core.viewmodel.NotificationPermissionViewModel
import com.example.atmos.ui.onboarding.components.GradientBackground

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier,
    viewModel: AlertsViewModel = hiltViewModel(),
    notificationPermissionViewModel: NotificationPermissionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState by notificationPermissionViewModel.state.collectAsStateWithLifecycle()
    val openAddAlertSheet by notificationPermissionViewModel.openAddAlertSheet.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }
    var hasDeclinedOnce by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        notificationPermissionViewModel.onEvent(
            NotificationPermissionEvent.OnPermissionRequestConsumed
        )
        if (isGranted) {
            notificationPermissionViewModel.onEvent(
                NotificationPermissionEvent.OnPermissionGranted
            )
        } else {
            if (hasDeclinedOnce) {
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnPermissionPermanentlyDenied
                )
            } else {
                hasDeclinedOnce = true
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnPermissionDenied
                )
            }
        }
    }

    LaunchedEffect(permissionState.triggerPermissionRequest) {
        if (permissionState.triggerPermissionRequest) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnPermissionRequestConsumed
                )
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnPermissionGranted
                )
            }
        }
    }

    LaunchedEffect(openAddAlertSheet) {
        if (openAddAlertSheet) {
            showBottomSheet = true
            notificationPermissionViewModel.onAlertSheetConsumed()
        }
    }

    if (permissionState.showPermissionRationaleDialog) {
        NotificationRationaleDialog(
            onConfirm = {
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnRationaleConfirmed
                )
            },
            onDismiss = {
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnRationaleDismissed
                )
            }
        )
    }

    if (permissionState.showPermanentDenyDialog) {
        NotificationPermanentDenyDialog(
            onConfirm = {
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnPermanentDenyConfirmed
                )
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            },
            onDismiss = {
                notificationPermissionViewModel.onEvent(
                    NotificationPermissionEvent.OnPermanentDenyDismissed
                )
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, bottom = 110.dp, start = 0.dp, end = 0.dp)
            ) {
                AlertsHeader(
                    onAddClick = {
                        notificationPermissionViewModel.onEvent(
                            NotificationPermissionEvent.OnAddAlertClicked
                        )
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (uiState.state) {
                    AlertsState.Loading -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(count = 4) {
                                AlertItemShimmer()
                            }
                        }
                    }

                    AlertsState.Success -> {
                        if (uiState.alerts.isEmpty()) {
                            AlertsEmptyContent()
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                items(
                                    items = uiState.alerts,
                                    key = { it.id }
                                ) { item ->
                                    key(item.id) {
                                        AlertItem(
                                            item = item,
                                            onDelete = {
                                                viewModel.onEvent(
                                                    AlertsEvent.OnDeleteAlert(item)
                                                )
                                            },
                                            onToggle = { isEnabled ->
                                                viewModel.onEvent(
                                                    AlertsEvent.OnToggleAlert(
                                                        id = item.id,
                                                        isEnabled = isEnabled
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is AlertsState.Error -> AlertsEmptyContent()
                }
            }
        }

        AlertsSnackbar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp),
            snackbarState = uiState.snackbarState,
            onUndo = { item ->
                viewModel.onEvent(AlertsEvent.OnUndoDelete(item))
            },
            onDismiss = {
                viewModel.onEvent(AlertsEvent.OnDismissSnackbar)
            }
        )
    }

    if (showBottomSheet) {
        CreateAlertBottomSheet(
            onDismiss = { showBottomSheet = false },
            onSave = { startTimeMs, endTimeMs, type ->
                viewModel.onEvent(
                    AlertsEvent.OnAddAlert(
                        startTimeMs = startTimeMs,
                        endTimeMs = endTimeMs,
                        type = type
                    )
                )
            }
        )
    }
}