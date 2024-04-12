package com.buggily.enemy.data.playlistWithTracks.di

import com.buggily.enemy.core.domain.GetInstantWithMetadata
import com.buggily.enemy.data.playlist.PlaylistRepositable
import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable
import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepository
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlaylistWithTracksRepositableProvider {

    @Provides
    fun provides(
        playlistRepository: PlaylistRepositable,
        playlistTrackRepository: PlaylistTrackRepositable,
        getInstantWithMetadata: GetInstantWithMetadata,
    ): PlaylistWithTracksRepositable = PlaylistWithTracksRepository(
        playlistRepository = playlistRepository,
        playlistTrackRepository = playlistTrackRepository,
        getInstantWithMetadata = getInstantWithMetadata,
    )
}