package com.buggily.enemy.ui.browse

data class BrowseState(
    val searchState: SearchState,
    val tabState: TabState,
) {

    data class SearchState(
        val value: String,
        val isVisible: Boolean,
        val onChange: (String) -> Unit,
        val onClear: () -> Unit,
        val onToggleVisibility: () -> Unit,
    ) {

        val isClearVisible: Boolean
            get() {
                val isDefault: Boolean = value == default.value
                return !isDefault
            }
        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String(),
                    isVisible = false,
                    onChange = {},
                    onClear = {},
                    onToggleVisibility = {},
                )
        }
    }

    data class TabState(
        val tab: Tab,
        val tabs: List<Tab>,
        val onClick: (Tab) -> Unit,
    ) {

        val index: Int
            get() = tabs.indexOf(tab)

        sealed class Tab {

            object Albums : Tab()
            object Tracks : Tab()

            companion object {
                val values: List<Tab>
                    get() = listOf(
                        Albums,
                        Tracks,
                    )
            }
        }

        companion object {
            val default: TabState
                get() = TabState(
                    tab = Tab.Albums,
                    tabs = Tab.values,
                    onClick = {},
                )
        }
    }

    companion object {
        val default: BrowseState
            get() = BrowseState(
                tabState = TabState.default,
                searchState = SearchState.default,
            )
    }
}
