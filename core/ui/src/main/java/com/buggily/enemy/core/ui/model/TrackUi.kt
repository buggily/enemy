package com.buggily.enemy.core.ui.model

sealed class TrackUi {

    data class Item(
        val track: com.buggily.enemy.data.track.Track,
    ) : TrackUi()

    sealed class Separator : TrackUi() {

        data class Disc(
            val disc: Int,
        ) : Separator()
    }
}
