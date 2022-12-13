package com.buggily.enemy.domain.album.di

import com.buggily.enemy.data.album.repository.AlbumRepositable
import com.buggily.enemy.domain.album.GetAlbumByAlbumId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetAlbumByAlbumIdProvider {

    @Provides
    fun provides(
        repository: AlbumRepositable,
    ): GetAlbumByAlbumId = GetAlbumByAlbumId(
        repository = repository,
    )
}
