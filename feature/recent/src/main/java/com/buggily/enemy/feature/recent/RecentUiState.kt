package com.buggily.enemy.feature.recent

import com.buggily.enemy.data.track.Track

data class RecentUiState(
    val trackState: TrackState,
) {

    data class TrackState(
        val onClick: (Track) -> Unit,
    )
}
