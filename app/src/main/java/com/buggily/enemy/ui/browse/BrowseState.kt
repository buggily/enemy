package com.buggily.enemy.ui.browse

data class BrowseState(
    val searchState: SearchState,
    val tabState: TabState,
) {

    data class SearchState(
        val value: String,
    ) {
        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String()
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

                val default: Tab
                    get() = Albums

                val values: List<Tab>
                    get() = listOf(
                        Albums,
                        Tracks,
                        Playlists,
                    )

                fun get(value: String?): Tab? = values.find {
                    it.toString() == value
                }
            }
        }

        companion object {
            val default: TabState
                get() = TabState(
                    tab = Tab.default,
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
