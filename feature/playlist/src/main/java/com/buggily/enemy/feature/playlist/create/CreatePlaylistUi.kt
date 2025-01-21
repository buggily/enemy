package com.buggily.enemy.feature.playlist.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.core.ui.ui.OutlinedSingleLineTextField
import com.buggily.enemy.core.ui.ui.TextButton
import com.buggily.enemy.feature.playlist.R
import com.buggily.enemy.core.ui.R as CR

@Composable
fun CreatePlaylistDialog(
    viewModel: CreatePlaylistViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: CreatePlaylistUiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreatePlaylistDialog(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun CreatePlaylistDialog(
    uiState: CreatePlaylistUiState,
    modifier: Modifier = Modifier,
) {
    CreatePlaylistDialog(
        nameState = uiState.nameState,
        confirmState = uiState.confirmState,
        modifier = modifier,
    )
}

@Composable
private fun CreatePlaylistDialog(
    nameState: CreatePlaylistUiState.NameState,
    confirmState: CreatePlaylistUiState.ConfirmState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(CR.dimen.padding_large),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large))
        ) {
            CreatePlaylistTitleText()

            CreatePlaylistNameTextField(
                nameState = nameState,
                modifier = Modifier.fillMaxWidth(),
            )

            CreatePlaylistConfirmTextButton(
                confirmState = confirmState,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Composable
private fun CreatePlaylistTitleText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.create_playlist),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}

@Composable
private fun CreatePlaylistNameTextField(
    nameState: CreatePlaylistUiState.NameState,
    modifier: Modifier = Modifier,
) {
    OutlinedSingleLineTextField(
        value = nameState.value,
        onValueChange = nameState.onChange,
        label = { CreatePlaylistNameLabel() },
        modifier = modifier,
    )
}

@Composable
private fun CreatePlaylistNameLabel(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.create_playlist_name),
        modifier = modifier,
    )
}

@Composable
private fun CreatePlaylistConfirmTextButton(
    confirmState: CreatePlaylistUiState.ConfirmState,
    modifier: Modifier = Modifier,
) {
    TextButton(
        text = stringResource(R.string.create_playlist_confirm),
        enabled = confirmState.isEnabled,
        onClick = confirmState.onClick,
        modifier = modifier,
    )
}
