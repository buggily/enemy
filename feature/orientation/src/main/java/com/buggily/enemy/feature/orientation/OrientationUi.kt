package com.buggily.enemy.feature.orientation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun OrientationScreen(
    albumsState: OrientationState.AlbumsState,
    viewModel: OrientationViewModel,
    modifier: Modifier = Modifier,
) {
    val state: OrientationState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    val permissionResult: Int = ContextCompat.checkSelfPermission(
        LocalContext.current,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        with(state) { if (it) grantState.onGrant() else denyState.onDeny() }
    }

    LaunchedEffect(Unit) {
        val permissionState: Flow<OrientationState.PermissionState> = viewModel.state.map {
            it.permissionState
        }

        permissionState.flowWithLifecycle(lifecycle).collect {
            when (it) {
                is OrientationState.PermissionState.Event -> {
                    when (it) {
                        is OrientationState.PermissionState.Event.Grant -> {
                            albumsState.onHomeClick()
                        }
                        is OrientationState.PermissionState.Event.Pend -> {
                            launcher.launch(viewModel.permission)
                        }
                        else -> Unit
                    }

                    it.onEvent()
                }
                else -> Unit
            }
        }
    }

    LaunchedEffect(Unit) {
        val isGranted: Boolean = permissionResult == PackageManager.PERMISSION_GRANTED
        with(state) { if (isGranted) grantState.onGrant() else pendState.onPend() }
    }

    OrientationScreen(
        permissionState = state.permissionState,
        modifier = modifier,
    )
}

@Composable
fun OrientationScreen(
    permissionState: OrientationState.PermissionState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        when (permissionState) {
            is OrientationState.PermissionState.Event.Deny -> OrientationScreenDeny()
            else -> OrientationScreenDefault()
        }
    }
}

@Composable
private fun OrientationScreenDefault() {}

@Composable
private fun OrientationScreenDeny() {}
