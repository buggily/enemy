package com.buggily.enemy.ui.browse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state: MutableStateFlow<BrowseState>
    val state: StateFlow<BrowseState> get() = _state

    val search: Flow<String>

    init {
        val isCreate: Boolean = savedStateHandle[isCreate] ?: false
        val tab: BrowseState.TabState.Tab = BrowseState.TabState.Tab.get(
            value = savedStateHandle[tab],
        ) ?: BrowseState.TabState.Tab.default

        BrowseState.default.copy(
            tabState = BrowseState.TabState.default.copy(
                tab = tab,
                onClick = ::onTabClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        val searchState: Flow<BrowseState.SearchState> = state.map {
            it.searchState
        }

        search = searchState.map {
            it.value
        }.distinctUntilChanged()
    }

    fun onSearchChange(value: String) = _state.update {
        val searchState: BrowseState.SearchState = it.searchState.copy(
            value = value,
        )

        it.copy(searchState = searchState)
    }

    private fun onTabClick(tab: BrowseState.TabState.Tab) = _state.update {
        val tabState: BrowseState.TabState = it.tabState.copy(
            tab = tab,
        )

        it.copy(tabState = tabState)
    }

    companion object {
        const val isCreate = "isCreate"
        const val tab = "tab"
    }
}
