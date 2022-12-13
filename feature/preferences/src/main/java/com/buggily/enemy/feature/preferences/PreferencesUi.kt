package com.buggily.enemy.feature.preferences

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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.core.ui.R.string as strings
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun PreferencesScreen(
    viewModel: PreferencesViewModel,
    modifier: Modifier,
) {
    val state: PreferencesState by viewModel.state.collectAsStateWithLifecycle()

    PreferencesScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
fun PreferencesScreen(
    state: PreferencesState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        item { PreferencesHeader(Modifier.padding(dimensionResource(dimens.padding_large))) }
        item { PreferencesContent(state) }
    }
}

@Composable
private fun PreferencesHeader(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(strings.settings),
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier,
    )
}

@Composable
private fun PreferencesContent(
    state: PreferencesState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(dimens.padding_large))

        PreferencesThemeScheme(
            schemeState = state.schemeState,
            modifier = contentModifier,
        )

        PreferencesThemeDynamic(
            dynamicState = state.dynamicState,
            modifier = contentModifier,
        )

        PreferencesThemeReset(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { state.resetState.onResetClick() }
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun PreferencesThemeScheme(
    schemeState: PreferencesState.SchemeState,
    modifier: Modifier = Modifier,
) {
    PreferencesPreference(
        text = stringResource(R.string.theme_scheme),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            schemeState.schemes.forEach {
                val scheme: Theme.Scheme = schemeState.scheme
                val isSelected: Boolean = it == scheme

                PreferencesThemeSchemeIconButton(
                    isSelected = isSelected,
                    scheme = it,
                    onSchemeClick = schemeState.onSchemeClick,
                )
            }
        }
    }
}

@Composable
private fun PreferencesThemeSchemeIconButton(
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
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
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
    dynamicState: PreferencesState.DynamicState,
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
        PreferencesPreferenceHeader(
            text = stringResource(R.string.theme_dynamic),
            modifier = Modifier.weight(1f),
        )

        Switch(
            checked = dynamicState.dynamic.isOn,
            onCheckedChange = dynamicState.onDynamicCheck,
        )
    }
}

@Composable
private fun PreferencesThemeReset(
    modifier: Modifier = Modifier,
) {
    PreferencesPreferenceHeader(
        text = stringResource(R.string.reset),
        modifier = modifier,
    )
}

@Composable
private fun PreferencesPreference(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        PreferencesPreferenceHeader(text)

        PreferencesContent(
            content = content,
            modifier = Modifier.padding(start = dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun PreferencesPreferenceHeader(
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
private fun PreferencesContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier) {
        content()
    }
}
