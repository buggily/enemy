package com.buggily.enemy.domain.album.di

import com.buggily.enemy.data.album.AlbumRepositable
import com.buggily.enemy.domain.album.GetAlbumById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object GetAlbumByIdProvider {

    @Provides
    fun provides(
        repository: AlbumRepositable,
    ): GetAlbumById = GetAlbumById(
        repository = repository,
    )
}
