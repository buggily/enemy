package com.buggily.enemy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.domain.theme.GetTheme
import com.buggily.enemy.domain.theme.ThemeUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EnemyViewModel @Inject constructor(getTheme: GetTheme) : ViewModel() {

    val theme: StateFlow<ThemeUi> = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ThemeUi(
            scheme = ThemeUi.Scheme.Default,
            dynamic = ThemeUi.Dynamic.On,
        ),
    )
}
