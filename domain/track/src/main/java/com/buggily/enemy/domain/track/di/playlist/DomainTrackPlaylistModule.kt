package com.buggily.enemy.domain.track.di.playlist

import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.domain.track.playlist.GetTrackByPlaylistIdAndIndex
import com.buggily.enemy.domain.track.playlist.GetTrackPagingByPlaylistId
import com.buggily.enemy.domain.track.playlist.GetTracksByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainTrackPlaylistModule {

    @Provides
    fun providesGetTrackByPlaylistIdAndIndex(
        playlistTrackRepository: PlaylistTrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackByPlaylistIdAndIndex = GetTrackByPlaylistIdAndIndex(
        playlistTrackRepository = playlistTrackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTrackPagingByPlaylistId(
        playlistTrackRepository: PlaylistTrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackPagingByPlaylistId = GetTrackPagingByPlaylistId(
        playlistTrackRepository = playlistTrackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTracksByPlaylistId(
        playlistTrackRepository: PlaylistTrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTracksByPlaylistId = GetTracksByPlaylistId(
        playlistTrackRepository = playlistTrackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )
}
