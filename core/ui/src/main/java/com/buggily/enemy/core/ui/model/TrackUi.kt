package com.buggily.enemy.core.ui.model

import com.buggily.enemy.data.track.Track

sealed class TrackUi {

    data class Item(
        val track: Track,
    ) : TrackUi()

    sealed class Separator : TrackUi() {

        data class Disc(
            val disc: Int,
        ) : Separator()
    }
}
