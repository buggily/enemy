package com.buggily.enemy.domain.album.di

import com.buggily.enemy.data.album.AlbumRepositable
import com.buggily.enemy.domain.album.GetAlbumById
import com.buggily.enemy.domain.album.GetAlbumPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainAlbumModule {

    @Provides
    fun providesGetAlbumById(
        albumRepository: AlbumRepositable,
    ): GetAlbumById = GetAlbumById(
        albumRepository = albumRepository,
    )

    @Provides
    fun providesGetAlbumPaging(
        albumRepository: AlbumRepositable,
    ): GetAlbumPaging = GetAlbumPaging(
        albumRepository = albumRepository,
    )
}
