package com.buggily.enemy.feature.orientation

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.buggily.enemy.core.ext.readPermission
import com.buggily.enemy.core.ui.TextButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun OrientationScreen(
    albumsState: OrientationState.AlbumsState,
    viewModel: OrientationViewModel,
    modifier: Modifier = Modifier,
) {
    val state: OrientationState by viewModel.state.collectAsStateWithLifecycle()

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.onResult(it)
    }

    LaunchedEffect(Unit) {
        val permissionState: Flow<OrientationState.PermissionState> = viewModel.state.map {
            it.permissionState
        }

        permissionState.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
            when (it) {
                is OrientationState.PermissionState.Event -> {
                    when (it) {
                        is OrientationState.PermissionState.Event.Grant -> {
                            albumsState.onGrant()
                        }
                        is OrientationState.PermissionState.Event.Pend -> {
                            launcher.launch(readPermission)
                        }
                    }

                    it.onEvent()
                }
                else -> Unit
            }
        }
    }

    OrientationScreen(
        statusState = state.statusState,
        modifier = modifier,
    ) {
        TextButton(
            text = stringResource(R.string.orientation_continue),
            modifier = Modifier.padding(dimensionResource(dimens.padding_large)),
        ) {
            ContextCompat.checkSelfPermission(
                context,
                readPermission
            ).let { viewModel.onResultCheck(it) }
        }
    }
}

@Composable
fun OrientationScreen(
    statusState: OrientationState.StatusState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    when (statusState) {
        is OrientationState.StatusState.Default -> OrientationScreenDefault(
            content = content,
            modifier = modifier,
        )
        is OrientationState.StatusState.Deny -> OrientationScreenDeny(
            content = content,
            modifier = modifier,
        )
    }
}

@Composable
private fun OrientationScreenDefault(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    OrientationScreenStatus(
        text = stringResource(R.string.orientation_default),
        content = content,
        modifier = modifier,
    )
}

@Composable
private fun OrientationScreenDeny(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    OrientationScreenStatus(
        text = stringResource(R.string.orientation_deny),
        content = content,
        modifier = modifier,
    )
}

@Composable
private fun OrientationScreenStatus(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
        ) {
            Text(
                text = stringResource(R.string.orientation_welcome),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = dimensionResource(dimens.padding_large),
                    alignment = Alignment.Top,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(dimens.padding_large)),
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                )

                content()
            }
        }
    }
}
