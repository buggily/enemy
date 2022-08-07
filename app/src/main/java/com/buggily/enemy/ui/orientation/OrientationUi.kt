package com.buggily.enemy.ui.orientation

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.buggily.enemy.ui.EnemyDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Composable
fun OrientationScreen(
    viewModel: OrientationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    val state: OrientationState by viewModel.state.collectAsStateWithLifecycle()

    val orientationState: OrientationState.State = state.state
    val grantState: OrientationState.State.GrantState = orientationState.grantState
    val pendState: OrientationState.State.PendState = orientationState.pendState
    val denyState: OrientationState.State.DenyState = orientationState.denyState

    val permissionResult: Int = ContextCompat.checkSelfPermission(
        LocalContext.current,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) grantState.onGrant() else denyState.onDeny()
    }

    LaunchedEffect(Unit) {
        val permissionState: Flow<OrientationState.PermissionState> = viewModel.state.map {
            it.permissionState
        }

        permissionState.flowWithLifecycle(lifecycle).collect {
            when (it) {
                is OrientationState.PermissionState.EventState -> {
                    when (it) {
                        is OrientationState.PermissionState.EventState.Grant -> {
                            navController.navigate(EnemyDestination.Home.route) {
                                val currentDestination: NavDestination = navController.currentDestination ?: return@navigate
                                val currentRoute: String = currentDestination.route ?: return@navigate

                                launchSingleTop = true
                                restoreState = false

                                popUpTo(currentRoute) {
                                    inclusive = true
                                    saveState = false
                                }
                            }
                        }
                        is OrientationState.PermissionState.EventState.Pend -> {
                            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
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
        when (permissionResult) {
            PackageManager.PERMISSION_GRANTED -> grantState.onGrant()
            else -> pendState.onPend()
        }
    }

    OrientationScreen(
        permissionState = state.permissionState,
        modifier = modifier,
    )
}

@Composable
private fun OrientationScreen(
    permissionState: OrientationState.PermissionState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        when (permissionState) {
            is OrientationState.PermissionState.EventState.Deny -> OrientationScreenDeny()
            else -> OrientationScreenDefault()
        }
    }
}

@Composable
private fun OrientationScreenDefault() {}

@Composable
private fun OrientationScreenDeny() {}
