package com.buggily.enemy.domain.track.di.playlist

import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.domain.track.playlist.GetTrackPagingByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object GetTrackPagingByPlaylistIdProvider {

    @Provides
    fun provides(
        playlistTrackRepository: PlaylistTrackRepositable,
    ): GetTrackPagingByPlaylistId = GetTrackPagingByPlaylistId(
        playlistTrackRepository = playlistTrackRepository,
    )
}
