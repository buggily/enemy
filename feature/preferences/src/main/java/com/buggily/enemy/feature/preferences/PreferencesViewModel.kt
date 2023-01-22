package com.buggily.enemy.feature.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.model.theme.Theme
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

    private val _state: MutableStateFlow<PreferencesState>
    val state: StateFlow<PreferencesState> get() = _state

    init {
        PreferencesState.default.copy(
            themeState = PreferencesState.ThemeState.default.copy(
                schemeState = PreferencesState.ThemeState.SchemeState.default.copy(
                    onSchemeClick = ::onThemeSchemeClick,
                    schemes = listOf(
                        Theme.Scheme.Default,
                        Theme.Scheme.Light,
                        Theme.Scheme.Dark,
                    ),
                ),
                dynamicState = PreferencesState.ThemeState.DynamicState.default.copy(
                    onDynamicCheck = ::onThemeDynamicCheck,
                ),
                resetState = PreferencesState.ThemeState.ResetState.default.copy(
                    onResetClick = ::onThemeResetClick,
                ),
            ),
        ).let { _state = MutableStateFlow(it) }

        viewModelScope.launch {
            getTheme().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Theme.default,
            ).collectLatest { setThemeState(it) }
        }
    }

    private fun onThemeSchemeClick(scheme: Theme.Scheme) = viewModelScope.launch {
        setThemeScheme(scheme)
    }

    private fun onThemeDynamicCheck(isChecked: Boolean) = viewModelScope.launch {
        setThemeDynamic(if (isChecked) { Theme.Dynamic.On } else { Theme.Dynamic.Off })
    }

    private fun onThemeResetClick() = viewModelScope.launch {
        setTheme(Theme.default)
    }

    private fun setThemeState(theme: Theme) = with(theme) {
        setSchemeState(scheme)
        setDynamicState(dynamic)
    }

    private fun setSchemeState(scheme: Theme.Scheme) = state.value.let {
        val themeState: PreferencesState.ThemeState = it.themeState
        val schemeState: PreferencesState.ThemeState.SchemeState = themeState.schemeState.copy(
            scheme = scheme,
        )

        setSchemeStateOfThemeState(schemeState)
    }

    private fun setDynamicState(dynamic: Theme.Dynamic) = state.value.let {
        val themeState: PreferencesState.ThemeState = it.themeState
        val dynamicState: PreferencesState.ThemeState.DynamicState = themeState.dynamicState.copy(
            dynamic = dynamic,
        )

        setDynamicStateOfThemeState(dynamicState)
    }

    private fun setSchemeStateOfThemeState(
        schemeState: PreferencesState.ThemeState.SchemeState,
    ) = state.value.let {
        val themeState: PreferencesState.ThemeState = it.themeState.copy(
            schemeState = schemeState,
        )

        setThemeState(themeState)
    }

    private fun setDynamicStateOfThemeState(
        dynamicState: PreferencesState.ThemeState.DynamicState,
    ) = state.value.let {
        val themeState: PreferencesState.ThemeState = it.themeState.copy(
            dynamicState = dynamicState,
        )

        setThemeState(themeState)
    }

    private fun setThemeState(themeState: PreferencesState.ThemeState) = _state.update {
        it.copy(themeState = themeState)
    }
}
