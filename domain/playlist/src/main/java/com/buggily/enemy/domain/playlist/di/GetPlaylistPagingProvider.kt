package com.buggily.enemy.domain.playlist.di

import com.buggily.enemy.data.playlist.repository.PlaylistRepositable
import com.buggily.enemy.domain.playlist.GetPlaylistPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetPlaylistPagingProvider {

    @Provides
    fun provides(
        repository: PlaylistRepositable,
    ): GetPlaylistPaging = GetPlaylistPaging(
        repository = repository,
    )
}
