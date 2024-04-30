package com.buggily.enemy.data.track

data class TrackWithIndex(
    val track: Track,
    val index: Int,
) {

    companion object {
        const val DEFAULT_INDEX = 0
        const val EMPTY_INDEX = -1
    }
}
