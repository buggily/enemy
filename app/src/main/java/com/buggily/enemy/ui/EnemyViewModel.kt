package com.buggily.enemy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.domain.theme.GetTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EnemyViewModel @Inject constructor(getTheme: GetTheme) : ViewModel() {

    private val _uiState: MutableStateFlow<EnemyUiState> = MutableStateFlow(EnemyUiState.default)
    val uiState: StateFlow<EnemyUiState> get() = _uiState

    val theme: StateFlow<Theme> = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Theme.default,
    )

    fun onDestinationChange(destination: NavDestination) = _uiState.update {
        it.copy(destinationState = it.destinationState.copy(destination = destination))
    }
}
