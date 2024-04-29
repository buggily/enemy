package com.buggily.enemy.tracks

import com.buggily.enemy.domain.track.TrackUi

data class TracksUiState(
    val trackState: TrackState,
    val searchState: SearchState,
) {

    data class TrackState(
        val onClick: (TrackUi) -> Unit,
        val onLongClick: (TrackUi) -> Unit,
    )

    data class SearchState(
        val value: String,
    )
}
