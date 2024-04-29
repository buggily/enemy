package com.buggily.enemy.feature.recent

import com.buggily.enemy.domain.track.TrackUi

data class RecentUiState(
    val trackState: TrackState,
) {

    data class TrackState(
        val onClick: (TrackUi) -> Unit,
    )
}
