package com.buggily.enemy.domain.track.playlist

import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.domain.track.TrackWithIndexUi
import com.buggily.enemy.domain.track.toUi

class GetTrackByPlaylistIdAndIndex(
    private val playlistTrackRepository: PlaylistTrackRepositable,
    private val getDuration: GetDuration,
    private val getDurationText: GetDurationText,
) {

    suspend operator fun invoke(
        playlistId: Long,
        index: Int,
    ): TrackWithIndexUi? = playlistTrackRepository.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )?.toUi(
        getDuration = getDuration,
        getDurationText = getDurationText,
    )
}
