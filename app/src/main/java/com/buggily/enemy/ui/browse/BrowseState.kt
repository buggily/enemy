package com.buggily.enemy.ui.browse

data class BrowseState(
    val tabState: TabState,
) {

    data class TabState(
        val tab: Tab,
        val tabs: List<Tab>,
        val onClick: (Tab) -> Unit,
    ) {

        val index: Int
            get() = tabs.indexOf(tab)

        companion object {
            val default: TabState
                get() = TabState(
                    tab = Tab.Albums,
                    tabs = Tab.values,
                    onClick = {},
                )
        }

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
    }

    companion object {
        val default: BrowseState
            get() = BrowseState(
                tabState = TabState.default,
            )
    }
}
