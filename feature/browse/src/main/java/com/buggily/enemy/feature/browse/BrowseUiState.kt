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

        sealed interface Tab {

            data object Albums : Tab {
                override fun toString(): String = "albums"
            }

            data object Tracks : Tab {
                override fun toString(): String = "tracks"
            }

            data object Playlists : Tab {
                override fun toString(): String = "playlists"
            }

            companion object {
                val values: List<Tab> = listOf(Albums, Tracks, Playlists)
                fun get(value: String?): Tab? = values.find { it.toString() == value }
            }
        }
    }
}
