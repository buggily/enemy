package com.buggily.enemy.core.ui.model

import com.buggily.enemy.data.track.Track

sealed interface TrackUi {

    data class Item(
        val track: Track,
    ) : TrackUi

    sealed interface Separator : TrackUi {

        data class Disc(
            val disc: Int,
        ) : Separator
    }
}
