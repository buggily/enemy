package com.buggily.enemy.domain.playlistWithTracks.di

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable
import com.buggily.enemy.domain.playlistWithTracks.DeletePlaylistById
import com.buggily.enemy.domain.playlistWithTracks.DeleteTrackByIdAndPlaylistIdAndIndex
import com.buggily.enemy.domain.playlistWithTracks.InsertTrackByIdAndPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainPlaylistsWithTracksModule {

    @Provides
    fun providesDeletePlaylistById(
        playlistWithTracksRepository: PlaylistWithTracksRepositable,
    ): DeletePlaylistById = DeletePlaylistById(
        playlistWithTracksRepository = playlistWithTracksRepository,
    )

    @Provides
    fun providesDeleteTrackByIdAndPlaylistIdAndIndex(
        playlistWithTracksRepository: PlaylistWithTracksRepositable,
    ): DeleteTrackByIdAndPlaylistIdAndIndex = DeleteTrackByIdAndPlaylistIdAndIndex(
        playlistWithTracksRepository = playlistWithTracksRepository,
    )

    @Provides
    fun providesInsertTrackByPlaylistId(
        playlistWithTracksRepository: PlaylistWithTracksRepositable,
    ): InsertTrackByIdAndPlaylistId = InsertTrackByIdAndPlaylistId(
        playlistWithTracksRepository = playlistWithTracksRepository,
    )
}
