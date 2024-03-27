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
            val isSearchable: Boolean

            data object Recent : Tab {
                override val stringResId: Int = R.string.browse_recent
                override val isSearchable: Boolean = false
            }

            data object Albums : Tab {
                override val stringResId: Int = R.string.browse_albums
                override val isSearchable: Boolean = true
            }

            data object Tracks : Tab {
                override val stringResId: Int = R.string.browse_tracks
                override val isSearchable: Boolean = true
            }

            data object Playlists : Tab {
                override val stringResId: Int = R.string.browse_playlists
                override val isSearchable: Boolean = true
            }

            companion object {
                val values: List<Tab> = listOf(
                    Recent,
                    Albums,
                    Tracks,
                    Playlists,
                )

                fun get(value: String?): Tab? = values.find {
                    it.toString() == value
                }
            }
        }
    }
}
