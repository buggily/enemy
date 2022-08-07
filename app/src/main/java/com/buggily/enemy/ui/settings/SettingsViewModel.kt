package com.buggily.enemy.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.domain.theme.Theme
import com.buggily.enemy.domain.use.theme.GetTheme
import com.buggily.enemy.domain.use.theme.SetTheme
import com.buggily.enemy.domain.use.theme.dynamic.GetDynamic
import com.buggily.enemy.domain.use.theme.dynamic.SetDynamic
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
class SettingsViewModel @Inject constructor(
    getTheme: GetTheme,
    getDynamic: GetDynamic,
    private val setTheme: SetTheme,
    private val setDynamic: SetDynamic,
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsState>
    val state: StateFlow<SettingsState> get() = _state

    init {
        SettingsState.default.copy(
            themeState = SettingsState.ThemeState.default.copy(
                onThemeClick = ::onThemeClick,
                themes = listOf(
                    Theme.Default,
                    Theme.Light,
                    Theme.Dark,
                ),
            ),
            dynamicState = SettingsState.DynamicState.default.copy(
                onDynamicCheck = ::onDynamicCheck,
            ),
            resetState = SettingsState.ResetState.default.copy(
                onResetClick = ::onResetClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        viewModelScope.launch {
            getTheme().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Theme.Default,
            ).collectLatest { setThemeState(it) }
        }

        viewModelScope.launch {
            getDynamic().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Theme.Dynamic.Default,
            ).collectLatest { setDynamicState(it) }
        }
    }

    private fun onThemeClick(theme: Theme) = viewModelScope.launch {
        setTheme(theme)
    }

    private fun onDynamicCheck(isChecked: Boolean) = viewModelScope.launch {
        val dynamic: Theme.Dynamic = if (isChecked) Theme.Dynamic.On else Theme.Dynamic.Off
        setDynamic(dynamic)
    }

    private fun onResetClick() = viewModelScope.launch {
        setTheme(Theme.Default)
        setDynamic(Theme.Dynamic.Default)
    }

    private fun setThemeState(theme: Theme) = state.value.let {
        val themeState: SettingsState.ThemeState = it.themeState.copy(
            theme = theme,
        )

        setThemeState(themeState)
    }

    private fun setDynamicState(dynamic: Theme.Dynamic) = state.value.let {
        val dynamicState: SettingsState.DynamicState = it.dynamicState.copy(
            dynamic = dynamic,
        )

        setDynamicState(dynamicState)
    }

    private fun setThemeState(themeState: SettingsState.ThemeState) = _state.update {
        it.copy(themeState = themeState)
    }

    private fun setDynamicState(dynamicState: SettingsState.DynamicState) = _state.update {
        it.copy(dynamicState = dynamicState)
    }
}
