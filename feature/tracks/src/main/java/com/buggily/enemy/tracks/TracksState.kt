package com.buggily.enemy.tracks

import com.buggily.enemy.core.model.track.Track

data class TracksState(
    val searchState: SearchState,
) {

    data class SearchState(
        val value: String,
    ) {

        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String(),
                )
        }
    }

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

    companion object {
        val default: TracksState
            get() = TracksState(
                searchState = SearchState.default,
            )
    }
}
