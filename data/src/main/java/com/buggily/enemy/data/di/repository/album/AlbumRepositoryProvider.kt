package com.buggily.enemy.data.di.repository.album

import com.buggily.enemy.data.repository.album.AlbumRepository
import com.buggily.enemy.data.source.album.AlbumSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumRepositoryProvider {

    @Provides
    fun provides(
        source: AlbumSourceable,
    ): AlbumRepository = AlbumRepository(
        source = source,
    )
}
