package com.buggily.enemy.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.R
import com.buggily.enemy.domain.theme.Theme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier,
) {
    val state: SettingsState by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        item { SettingsHeader(Modifier.padding(dimensionResource(R.dimen.padding_large))) }
        item { SettingsContent(state) }
    }
}

@Composable
private fun SettingsHeader(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.settings),
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier,
    )
}

@Composable
private fun SettingsContent(
    state: SettingsState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_large))

        ThemeSetting(
            themeState = state.themeState,
            modifier = contentModifier,
        )

        DynamicSetting(
            dynamicState = state.dynamicState,
            modifier = contentModifier,
        )

        ResetSetting(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { state.resetState.onResetClick() }
                .padding(dimensionResource(R.dimen.padding_large)),
        )
    }
}

@Composable
private fun ThemeSetting(
    themeState: SettingsState.ThemeState,
    modifier: Modifier = Modifier,
) {
    SettingsSetting(
        text = stringResource(R.string.theme),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            themeState.themes.forEach {
                val theme: Theme = themeState.theme
                val isSelected: Boolean = it == theme

                ThemeSettingRadioButton(
                    isSelected = isSelected,
                    theme = it,
                    onThemeClick = themeState.onThemeClick,
                )
            }
        }
    }
}

@Composable
private fun ThemeSettingRadioButton(
    isSelected: Boolean,
    theme: Theme,
    onThemeClick: (Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val text: String = when (theme) {
        is Theme.Default -> R.string.theme_default
        is Theme.Dark -> R.string.theme_dark
        is Theme.Light -> R.string.theme_light
    }.let { stringResource(it) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )

        RadioButton(
            selected = isSelected,
            onClick = { onThemeClick(theme) },
            modifier = Modifier.semantics { contentDescription = text },
        )
    }
}

@Composable
private fun DynamicSetting(
    dynamicState: SettingsState.DynamicState,
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
        SettingHeader(
            text = stringResource(R.string.dynamic),
            modifier = Modifier.weight(1f),
        )

        val isChecked: Boolean = when (dynamicState.dynamic) {
            is Theme.Dynamic.Off -> false
            else -> true
        }

        Switch(
            checked = isChecked,
            onCheckedChange = dynamicState.onDynamicCheck,
        )
    }
}

@Composable
private fun ResetSetting(
    modifier: Modifier = Modifier,
) {
    SettingHeader(
        text = stringResource(R.string.reset),
        modifier = modifier,
    )
}

@Composable
private fun SettingsSetting(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        SettingHeader(text)

        SettingContent(
            content = content,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_large)),
        )
    }
}

@Composable
private fun SettingHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}

@Composable
private fun SettingContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier) {
        content()
    }
}
