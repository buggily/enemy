package com.buggily.enemy.domain.playlist.di

import com.buggily.enemy.data.playlist.repository.PlaylistRepositable
import com.buggily.enemy.domain.playlist.UpdatePlaylist
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UpdatePlaylistProvider {

    @Provides
    fun provides(
        repository: PlaylistRepositable,
    ): UpdatePlaylist = UpdatePlaylist(
        repository = repository,
    )
}