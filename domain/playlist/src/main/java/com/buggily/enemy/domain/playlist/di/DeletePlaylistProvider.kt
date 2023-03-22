package com.buggily.enemy.domain.playlist.di

import com.buggily.enemy.data.playlist.repository.PlaylistRepositable
import com.buggily.enemy.domain.playlist.DeletePlaylist
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DeletePlaylistProvider {

    @Provides
    fun provides(
        repository: PlaylistRepositable,
    ): DeletePlaylist = DeletePlaylist(
        repository = repository,
    )
}
