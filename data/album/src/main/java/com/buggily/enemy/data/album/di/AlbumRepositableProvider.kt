package com.buggily.enemy.data.album.di

import com.buggily.enemy.data.album.AlbumRepositable
import com.buggily.enemy.data.album.AlbumRepository
import com.buggily.enemy.external.album.ExternalAlbumSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AlbumRepositableProvider {

    @Provides
    fun provides(
        albumExternalSource: ExternalAlbumSourceable,
    ): AlbumRepositable = AlbumRepository(
        albumExternalSource = albumExternalSource
    )
}
