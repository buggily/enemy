package com.buggily.enemy.feature.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.domain.theme.GetTheme
import com.buggily.enemy.domain.theme.SetTheme
import com.buggily.enemy.domain.theme.SetThemeDynamic
import com.buggily.enemy.domain.theme.SetThemeScheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    getTheme: GetTheme,
    private val setTheme: SetTheme,
    private val setThemeScheme: SetThemeScheme,
    private val setThemeDynamic: SetThemeDynamic,
) : ViewModel() {

    private val _uiState: MutableStateFlow<PreferencesUiState>
    val uiState: StateFlow<PreferencesUiState> get() = _uiState

    init {
        PreferencesUiState(
            themeState = PreferencesUiState.ThemeState(
                schemeState = PreferencesUiState.ThemeState.SchemeState(
                    scheme = Theme.Scheme.Default,
                    schemes = schemes,
                    onClick = ::onThemeSchemeClick,
                ),
                dynamicState = PreferencesUiState.ThemeState.DynamicState(
                    dynamic = Theme.Dynamic.On,
                    onCheck = ::onThemeDynamicCheck,
                ),
                resetState = PreferencesUiState.ThemeState.ResetState(
                    onClick = ::onThemeResetClick,
                ),
            ),
        ).let { _uiState = MutableStateFlow(it) }

        viewModelScope.launch {
            getTheme().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Theme(
                    scheme = Theme.Scheme.Default,
                    dynamic = Theme.Dynamic.On,
                ),
            ).collectLatest { setThemeState(it) }
        }
    }

    private fun onThemeSchemeClick(scheme: Theme.Scheme) = viewModelScope.launch {
        setThemeScheme(scheme)
    }

    private fun onThemeDynamicCheck(isChecked: Boolean) = viewModelScope.launch {
        setThemeDynamic(if (isChecked) Theme.Dynamic.On else Theme.Dynamic.Off)
    }

    private fun onThemeResetClick() = viewModelScope.launch {
        Theme(
            scheme = Theme.Scheme.Default,
            dynamic = Theme.Dynamic.On,
        ).let { setTheme(it) }
    }

    private fun setThemeState(theme: Theme) = with(theme) {
        setSchemeState(scheme)
        setDynamicState(dynamic)
    }

    private fun setSchemeState(scheme: Theme.Scheme) = _uiState.update {
        val schemeState: PreferencesUiState.ThemeState.SchemeState =
            it.themeState.schemeState.copy(scheme = scheme)

        it.copy(themeState = it.themeState.copy(schemeState = schemeState))
    }

    private fun setDynamicState(dynamic: Theme.Dynamic) = _uiState.update {
        val dynamicState: PreferencesUiState.ThemeState.DynamicState =
            it.themeState.dynamicState.copy(dynamic = dynamic)

        it.copy(themeState = it.themeState.copy(dynamicState = dynamicState))
    }

    private val schemes: List<Theme.Scheme>
        get() = listOf(
            Theme.Scheme.Default,
            Theme.Scheme.Light,
            Theme.Scheme.Dark,
        )
}
