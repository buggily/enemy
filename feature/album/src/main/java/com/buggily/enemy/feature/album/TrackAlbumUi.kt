package com.buggily.enemy.feature.album

import com.buggily.enemy.domain.track.TrackUi

sealed interface TrackAlbumUi {

    data class Item(
        val track: TrackUi,
    ) : TrackAlbumUi

    sealed interface Separator : TrackAlbumUi {

        data class Disc(
            val disc: Int,
        ) : Separator {

            val text: String
                get() = disc.toString()
        }
    }
}
