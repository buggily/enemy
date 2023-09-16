package com.buggily.enemy.tracks

import com.buggily.enemy.data.track.Track

data class TracksUiState(
    val trackState: TrackState,
    val searchState: SearchState,
) {

    data class SearchState(
        val value: String,
    )

    data class TrackState(
        val onClick: (Track) -> Unit,
        val onLongClick: (Track) -> Unit,
    )
}
