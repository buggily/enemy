package com.buggily.enemy.feature.playlist.edit

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
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ui.OutlinedSingleLineTextField
import com.buggily.enemy.core.ui.ui.TextButton
import com.buggily.enemy.feature.playlist.R.string

@Composable
fun EditPlaylistDialog(
    viewModel: EditPlaylistViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: EditPlaylistUiState by viewModel.uiState.collectAsStateWithLifecycle()

    EditPlaylistDialog(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun EditPlaylistDialog(
    uiState: EditPlaylistUiState,
    modifier: Modifier = Modifier,
) {
    EditPlaylistDialog(
        nameState = uiState.nameState,
        confirmState = uiState.confirmState,
        modifier = modifier,
    )
}

@Composable
private fun EditPlaylistDialog(
    nameState: EditPlaylistUiState.NameState,
    confirmState: EditPlaylistUiState.ConfirmState,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.padding_large),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
        ) {
            EditPlaylistTitleText()

            EditPlaylistNameTextField(
                nameState = nameState,
                modifier = Modifier.fillMaxWidth(),
            )

            EditPlaylistConfirmTextButton(
                confirmState = confirmState,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Composable
private fun EditPlaylistTitleText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(string.edit_playlist),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}

@Composable
private fun EditPlaylistNameTextField(
    nameState: EditPlaylistUiState.NameState,
    modifier: Modifier = Modifier,
) {
    OutlinedSingleLineTextField(
        value = nameState.value,
        onValueChange = nameState.onChange,
        label = { EditPlaylistNameLabel() },
        modifier = modifier,
    )
}

@Composable
private fun EditPlaylistNameLabel(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(string.edit_playlist_name),
        modifier = modifier,
    )
}

@Composable
private fun EditPlaylistConfirmTextButton(
    confirmState: EditPlaylistUiState.ConfirmState,
    modifier: Modifier = Modifier,
) {
    TextButton(
        text = stringResource(string.edit_playlist_confirm),
        enabled = confirmState.isEnabled,
        onClick = confirmState.onClick,
        modifier = modifier,
    )
}

