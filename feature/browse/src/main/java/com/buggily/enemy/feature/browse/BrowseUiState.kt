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

            val stringResId: Int

            data object Albums : Tab {
                override val stringResId: Int = R.string.browse_albums
            }

            data object Tracks : Tab {
                override val stringResId: Int = R.string.browse_tracks
            }

            data object Playlists : Tab {
                override val stringResId: Int = R.string.browse_playlists
            }

            companion object {
                val values: List<Tab> = listOf(Albums, Tracks, Playlists)
                fun get(value: String?): Tab? = values.find { it.toString() == value }
            }
        }
    }
}
