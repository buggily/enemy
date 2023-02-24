package com.buggily.enemy.ui.browse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state: MutableStateFlow<BrowseState>
    val state: StateFlow<BrowseState> get() = _state

    init {
        val tab: BrowseState.TabState.Tab = BrowseState.TabState.Tab.get(
            value = savedStateHandle[tab],
        ) ?: BrowseState.TabState.Tab.Albums

        BrowseState.default.copy(
            tabState = BrowseState.TabState.default.copy(
                tab = tab,
                onClick = ::onTabClick,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    fun onFloatingActionButtonClick() {
        onTabClick(BrowseState.TabState.Tab.Playlists)
    }

    private fun onTabClick(tab: BrowseState.TabState.Tab) = _state.update {
        val tabState: BrowseState.TabState = it.tabState.copy(
            tab = tab,
        )

        it.copy(tabState = tabState)
    }

    companion object {
        const val tab = "tab"
    }
}
