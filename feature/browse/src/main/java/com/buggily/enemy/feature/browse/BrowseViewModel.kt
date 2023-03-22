package com.buggily.enemy.feature.browse

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BrowseViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<BrowseUiState>
    val uiState: StateFlow<BrowseUiState> get() = _uiState

    init {
        BrowseUiState.default.copy(
            tabState = BrowseUiState.TabState.default.copy(
                onClick = ::onTabClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }
    }

    private fun onTabClick(tab: BrowseUiState.TabState.Tab) = _uiState.update {
        it.copy(tabState = it.tabState.copy(tab = tab))
    }
}
