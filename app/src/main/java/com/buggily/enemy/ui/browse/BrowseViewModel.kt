package com.buggily.enemy.ui.browse

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<BrowseState>
    val state: StateFlow<BrowseState> get() = _state

    init {
        BrowseState.default.copy(
            tabState = BrowseState.TabState.default.copy(
                onClick = ::onTabClick,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    private fun onTabClick(
        tab: BrowseState.TabState.Tab,
    ) = state.value.let {
        val tabState: BrowseState.TabState = it.tabState.copy(
            tab = tab,
        )

        setTabState(tabState)
    }

    private fun setTabState(
        tabState: BrowseState.TabState,
    ) = _state.update {
        it.copy(tabState = tabState)
    }
}
