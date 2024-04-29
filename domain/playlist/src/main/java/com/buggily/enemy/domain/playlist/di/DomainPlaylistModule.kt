package com.buggily.enemy.domain.playlist.di

import com.buggily.enemy.core.domain.GetInstantText
import com.buggily.enemy.data.playlist.PlaylistRepositable
import com.buggily.enemy.domain.playlist.CreatePlaylist
import com.buggily.enemy.domain.playlist.GetPlaylistById
import com.buggily.enemy.domain.playlist.GetPlaylistPaging
import com.buggily.enemy.domain.playlist.UpdatePlaylistById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainPlaylistModule {

    @Provides
    fun providesCreatePlaylist(
        playlistRepository: PlaylistRepositable,
    ): CreatePlaylist = CreatePlaylist(
        playlistRepository = playlistRepository,
    )

    @Provides
    fun providesGetPlaylistById(
        playlistRepository: PlaylistRepositable,
        getInstantText: GetInstantText,
    ): GetPlaylistById = GetPlaylistById(
        playlistRepository = playlistRepository,
        getInstantText = getInstantText,
    )

    @Provides
    fun providesGetPlaylistPaging(
        playlistRepository: PlaylistRepositable,
        getInstantText: GetInstantText,
    ): GetPlaylistPaging = GetPlaylistPaging(
        playlistRepository = playlistRepository,
        getInstantText = getInstantText,
    )

    @Provides
    fun providesUpdatePlaylistById(
        playlistRepository: PlaylistRepositable,
    ): UpdatePlaylistById = UpdatePlaylistById(
        playlistRepository = playlistRepository,
    )
}
