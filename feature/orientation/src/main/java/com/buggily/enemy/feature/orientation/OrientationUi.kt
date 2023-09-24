package com.buggily.enemy.feature.orientation

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.buggily.enemy.core.ext.readPermission

@Composable
fun OrientationScreen(
    viewModel: OrientationViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: OrientationUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.onResult(it)
    }

    LaunchedEffect(viewModel) {
        viewModel.event.flowWithLifecycle(lifecycle).collect {
            when (it) {
                is OrientationEvent.ReadPermission -> launcher.launch(readPermission)
            }
        }
    }

    Box(modifier) {
        OrientationScreen(
            uiState = uiState,
            modifier = Modifier
                .align(Alignment.Center)
                .safeContentPadding(),
        )
    }
}

@Composable
private fun OrientationScreen(
    uiState: OrientationUiState,
    modifier: Modifier = Modifier,
) {
    OrientationScreen(
        permissionState = uiState.permissionState,
        modifier = modifier,
    )
}

@Composable
private fun OrientationScreen(
    permissionState: OrientationUiState.PermissionState,
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current

    Box(modifier) {
        Button(
            onClick = {
                ContextCompat.checkSelfPermission(
                    context,
                    readPermission
                ).let { permissionState.onClick(it) }
            },
            modifier = Modifier,
        ) {
            Text(
                text = stringResource(R.string.orientation_continue),
                modifier = Modifier,
            )
        }
    }
}
