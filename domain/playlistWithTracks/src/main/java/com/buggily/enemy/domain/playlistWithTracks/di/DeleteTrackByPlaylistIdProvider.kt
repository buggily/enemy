package com.buggily.enemy.domain.playlistWithTracks.di

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable
import com.buggily.enemy.domain.playlistWithTracks.DeleteTrackByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DeleteTrackByPlaylistIdProvider {

    @Provides
    fun provides(
        playlistWithTracksRepository: PlaylistWithTracksRepositable,
    ): DeleteTrackByPlaylistId = DeleteTrackByPlaylistId(
        playlistWithTracksRepository = playlistWithTracksRepository,
    )
}
