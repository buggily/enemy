package com.buggily.enemy.data.album.di.repository

import com.buggily.enemy.data.album.repository.AlbumRepository
import com.buggily.enemy.data.album.source.AlbumExternalSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumRepositoryProvider {

    @Provides
    fun provides(
        source: AlbumExternalSourceable,
    ): AlbumRepository = AlbumRepository(
        source = source,
    )
}
