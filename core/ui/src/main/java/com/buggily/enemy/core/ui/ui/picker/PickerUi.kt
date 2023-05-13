package com.buggily.enemy.core.ui.ui.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ui.ItemRow
import com.buggily.enemy.core.ui.ui.ItemTextMajor

@Composable
fun PickerDialog(
    viewModel: PickerViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: PickerUiState by viewModel.uiState.collectAsStateWithLifecycle()

    PickerDialog(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun PickerDialog(
    uiState: PickerUiState,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            uiState.values.forEach {
                PickerItem(
                    picker = it,
                    onClick = { uiState.onClick(it) },
                )
            }
        }
    }
}

@Composable
private fun PickerItem(
    picker: Pickerable,
    onClick: () -> Unit,
) {
    ItemRow(onClick) {
        PickerItemContent(
            picker = picker,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PickerItemContent(
    picker: Pickerable,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(picker.painterResId),
            contentDescription = stringResource(picker.contentDescriptionResId),
            modifier = Modifier.size(dimensionResource(R.dimen.icon_medium)),
        )

        ItemTextMajor(
            text = stringResource(picker.stringResId),
            textAlign = TextAlign.Start,
        )
    }
}

