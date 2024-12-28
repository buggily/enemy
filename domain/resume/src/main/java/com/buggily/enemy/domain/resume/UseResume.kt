package com.buggily.enemy.domain.resume

import androidx.media3.common.MediaItem
import com.buggily.enemy.domain.track.GetTrackById
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.domain.track.playlist.GetTracksByPlaylistId

class UseResume(
    private val getTrackById: GetTrackById,
    private val getTracksByAlbumId: GetTracksByAlbumId,
    private val getTracksByPlaylistId: GetTracksByPlaylistId,
) {

    suspend operator fun invoke(
        resume: ResumeUi,
    ): List<MediaItem> = when (resume.source) {
        is ResumeUi.Source.Track -> getTrackById(resume.id)?.let { listOf(it) }
        is ResumeUi.Source.Album -> getTracksByAlbumId(resume.id)
        is ResumeUi.Source.Playlist -> getTracksByPlaylistId(resume.id).map { it.track }
        else -> null
    }?.map { it.toMediaItem() }.orEmpty()
}
