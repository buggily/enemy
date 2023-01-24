package com.buggily.enemy.core.model.track

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
