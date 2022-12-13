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
            schemeState = PreferencesState.SchemeState.default.copy(
                onSchemeClick = ::onSchemeClick,
                schemes = listOf(
                    Theme.Scheme.Default,
                    Theme.Scheme.Light,
                    Theme.Scheme.Dark,
                ),
            ),
            dynamicState = PreferencesState.DynamicState.default.copy(
                onDynamicCheck = ::onDynamicCheck,
            ),
            resetState = PreferencesState.ResetState.default.copy(
                onResetClick = ::onResetClick,
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

    private fun onSchemeClick(scheme: Theme.Scheme) = viewModelScope.launch {
        setThemeScheme(scheme)
    }

    private fun onDynamicCheck(isChecked: Boolean) = viewModelScope.launch {
        setThemeDynamic(if (isChecked) { Theme.Dynamic.On } else { Theme.Dynamic.Off })
    }

    private fun onResetClick() = viewModelScope.launch {
        setTheme(Theme.default)
    }

    private fun setThemeState(theme: Theme) = with(theme) {
        setSchemeState(scheme)
        setDynamicState(dynamic)
    }

    private fun setSchemeState(scheme: Theme.Scheme) = state.value.let {
        val schemeState: PreferencesState.SchemeState = it.schemeState.copy(
            scheme = scheme,
        )

        setSchemeState(schemeState)
    }

    private fun setDynamicState(dynamic: Theme.Dynamic) = state.value.let {
        val dynamicState: PreferencesState.DynamicState = it.dynamicState.copy(
            dynamic = dynamic,
        )

        setDynamicState(dynamicState)
    }

    private fun setSchemeState(schemeState: PreferencesState.SchemeState) = _state.update {
        it.copy(schemeState = schemeState)
    }

    private fun setDynamicState(dynamicState: PreferencesState.DynamicState) = _state.update {
        it.copy(dynamicState = dynamicState)
    }
}
