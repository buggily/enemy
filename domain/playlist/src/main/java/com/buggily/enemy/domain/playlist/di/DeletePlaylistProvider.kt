package com.buggily.enemy.domain.playlist.di

import com.buggily.enemy.data.playlist.PlaylistRepositable
import com.buggily.enemy.domain.playlist.DeletePlaylistById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DeletePlaylistProvider {

    @Provides
    fun provides(
        repository: PlaylistRepositable,
    ): DeletePlaylistById = DeletePlaylistById(
        repository = repository,
    )
}
