package com.buggily.enemy.tracks

import com.buggily.enemy.core.model.track.Track

object TracksState {

    data class TrackState(
        val onClick: (Track) -> Unit,
    ) {

        companion object {
            val default: TrackState
                get() = TrackState(
                    onClick = {},
                )
        }
    }
}
