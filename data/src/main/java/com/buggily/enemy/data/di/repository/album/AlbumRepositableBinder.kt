package com.buggily.enemy.data.di.repository.album

import com.buggily.enemy.data.repository.album.AlbumRepositable
import com.buggily.enemy.data.repository.album.AlbumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AlbumRepositableBinder {

    @Binds
    fun binds(
        repository: AlbumRepository,
    ): AlbumRepositable
}
