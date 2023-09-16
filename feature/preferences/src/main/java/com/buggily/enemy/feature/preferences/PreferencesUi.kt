package com.buggily.enemy.feature.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.core.ui.ui.SingleLineText
import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
fun PreferencesScreen(
    viewModel: PreferencesViewModel,
    modifier: Modifier,
) {
    val uiState: PreferencesUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier) {
        PreferencesScreen(
            uiState = uiState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun PreferencesScreen(
    uiState: PreferencesUiState,
    modifier: Modifier = Modifier,
) {
    PreferencesScreen(
        themeState = uiState.themeState,
        modifier = modifier,
    )
}

@Composable
private fun PreferencesScreen(
    themeState: PreferencesUiState.ThemeState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large_extra),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        contentPadding = WindowInsets(
            left = dimensionResource(dimens.padding_large),
            top = dimensionResource(dimens.padding_large),
            right = dimensionResource(dimens.padding_large),
            bottom = dimensionResource(dimens.padding_large),
        )
            .add(WindowInsets.safeContent)
            .asPaddingValues(),
        modifier = modifier.consumeWindowInsets(WindowInsets.safeContent),
    ) {
        item {
            PreferencesHeader(
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            PreferencesContent(
                themeState = themeState,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PreferencesHeader(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(R.string.preferences),
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier,
    )
}

@Composable
private fun PreferencesContent(
    themeState: PreferencesUiState.ThemeState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        PreferencesThemeContent(
            themeState = themeState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PreferencesThemeContent(
    themeState: PreferencesUiState.ThemeState,
    modifier: Modifier = Modifier,
) {
    PreferencesPreference(
        text = stringResource(R.string.theme_scheme),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PreferencesThemeDynamic(
                dynamicState = themeState.dynamicState,
                modifier = Modifier.fillMaxWidth(),
            )

            PreferencesThemeScheme(
                schemeState = themeState.schemeState,
                modifier = Modifier.fillMaxWidth(),
            )

            PreferencesThemeReset(
                resetState = themeState.resetState,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Composable
private fun PreferencesThemeScheme(
    schemeState: PreferencesUiState.ThemeState.SchemeState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        schemeState.schemes.forEach {
            val scheme: Theme.Scheme = schemeState.scheme
            val isSelected: Boolean = it == scheme

            PreferencesThemeSchemeRadioButton(
                isSelected = isSelected,
                scheme = it,
                onSchemeClick = schemeState.onClick,
            )
        }
    }
}

@Composable
private fun PreferencesThemeSchemeRadioButton(
    isSelected: Boolean,
    scheme: Theme.Scheme,
    onSchemeClick: (Theme.Scheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val text: String = when (scheme) {
        is Theme.Scheme.Default -> R.string.theme_scheme_default
        is Theme.Scheme.Dark -> R.string.theme_scheme_dark
        is Theme.Scheme.Light -> R.string.theme_scheme_light
    }.let { stringResource(it) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        SingleLineText(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1f),
        )

        RadioButton(
            selected = isSelected,
            onClick = { onSchemeClick(scheme) },
            modifier = Modifier.semantics { contentDescription = text },
        )
    }
}

@Composable
private fun PreferencesThemeDynamic(
    dynamicState: PreferencesUiState.ThemeState.DynamicState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        SingleLineText(
            text = stringResource(R.string.theme_dynamic),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1f),
        )

        Switch(
            checked = dynamicState.dynamic is Theme.Dynamic.On,
            onCheckedChange = dynamicState.onCheck,
        )
    }
}

@Composable
private fun PreferencesThemeReset(
    resetState: PreferencesUiState.ThemeState.ResetState,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = resetState.onClick,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.theme_reset),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun PreferencesPreference(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(dimens.padding_large)),
        ) {
            SingleLineText(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier,
            )

            Box(Modifier.padding(horizontal = dimensionResource(dimens.padding_large))) {
                content()
            }
        }
    }
}
