package com.buggily.enemy.data.album.di.repository

import com.buggily.enemy.data.album.repository.AlbumRepositable
import com.buggily.enemy.data.album.repository.AlbumRepository
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
