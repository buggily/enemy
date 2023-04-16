package com.buggily.enemy.feature.browse

data class BrowseUiState(
    val tabState: TabState,
) {

    data class TabState(
        val tab: Tab,
        val tabs: List<Tab>,
        val onClick: (Tab) -> Unit,
    ) {

        val index: Int
            get() = tabs.indexOf(tab)

        sealed class Tab {

            object Albums : Tab() {
                override fun toString(): String = "albums"
            }
            object Tracks : Tab() {
                override fun toString(): String = "tracks"
            }

            object Playlists : Tab() {
                override fun toString(): String = "playlists"
            }

            companion object {
                val values: List<Tab> get() = listOf(Albums, Tracks, Playlists)
                fun get(value: String?): Tab? = values.find { it.toString() == value }
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
        val default: BrowseUiState
            get() = BrowseUiState(
                tabState = TabState.default,
            )
    }
}
