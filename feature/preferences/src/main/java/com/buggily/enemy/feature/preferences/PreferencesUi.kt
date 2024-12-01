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
import com.buggily.enemy.domain.theme.ThemeUi
import com.buggily.enemy.core.ui.R as CR

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
        recentsState = uiState.recentsState,
        modifier = modifier,
    )
}

@Composable
private fun PreferencesScreen(
    themeState: PreferencesUiState.ThemeState,
    recentsState: PreferencesUiState.RecentsState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        contentPadding = WindowInsets(
            left = dimensionResource(CR.dimen.padding_large),
            top = dimensionResource(CR.dimen.padding_large),
            right = dimensionResource(CR.dimen.padding_large),
            bottom = dimensionResource(CR.dimen.padding_large),
        )
            .add(WindowInsets.safeContent)
            .asPaddingValues(),
        modifier = modifier.consumeWindowInsets(WindowInsets.safeContent),
    ) {
        item { PreferencesPreferencesHeader() }

        item {
            PreferencesThemeContent(
                themeState = themeState,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            PreferencesRecentsContent(
                recentsState = recentsState,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PreferencesPreferencesHeader(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(R.string.preferences_preferences),
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier,
    )
}

@Composable
private fun PreferencesThemeContent(
    themeState: PreferencesUiState.ThemeState,
    modifier: Modifier = Modifier,
) {
    PreferencesPreference(
        text = stringResource(R.string.preferences_theme),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(CR.dimen.padding_medium),
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
                themeState = themeState,
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
            val scheme: ThemeUi.Scheme = schemeState.scheme
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
    scheme: ThemeUi.Scheme,
    onSchemeClick: (ThemeUi.Scheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val text: String = when (scheme) {
        is ThemeUi.Scheme.Default -> R.string.preferences_theme_scheme_default
        is ThemeUi.Scheme.Dark -> R.string.preferences_theme_scheme_dark
        is ThemeUi.Scheme.Light -> R.string.preferences_theme_scheme_light
    }.let { stringResource(it) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
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
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        SingleLineText(
            text = stringResource(R.string.preferences_theme_dynamic),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1f),
        )

        Switch(
            checked = dynamicState.dynamic is ThemeUi.Dynamic.On,
            onCheckedChange = dynamicState.onCheck,
        )
    }
}

@Composable
private fun PreferencesThemeReset(
    themeState: PreferencesUiState.ThemeState,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = themeState.onResetClick,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.preferences_theme_reset),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun PreferencesRecentsContent(
    recentsState: PreferencesUiState.RecentsState,
    modifier: Modifier = Modifier,
) {
    PreferencesPreference(
        text = stringResource(R.string.preferences_recents),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(CR.dimen.padding_medium),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PreferencesRecentsTrackReset(
                recentsState = recentsState,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Composable
private fun PreferencesRecentsTrackReset(
    recentsState: PreferencesUiState.RecentsState,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = recentsState.onResetClick,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.preferences_recents_reset),
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
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(CR.dimen.padding_large),
                alignment = Alignment.Top,
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CR.dimen.padding_large)),
        ) {
            SingleLineText(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier,
            )

            Box(Modifier.padding(horizontal = dimensionResource(CR.dimen.padding_large))) {
                content()
            }
        }
    }
}
