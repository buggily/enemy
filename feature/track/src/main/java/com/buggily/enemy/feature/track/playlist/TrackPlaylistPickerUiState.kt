package com.buggily.enemy.feature.track.playlist

import com.buggily.enemy.domain.playlist.PlaylistUi

data class TrackPlaylistPickerUiState(
    val playlistState: PlaylistState,
) {

    data class PlaylistState(
        val onClick: (PlaylistUi) -> Unit,
    )
}
