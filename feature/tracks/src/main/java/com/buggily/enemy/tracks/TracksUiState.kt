package com.buggily.enemy.tracks

import com.buggily.enemy.core.model.track.Track

data class TracksUiState(
    val trackState: TrackState,
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
        val default: TracksUiState
            get() = TracksUiState(
                trackState = TrackState.default,
                searchState = SearchState.default,
            )
    }
}
