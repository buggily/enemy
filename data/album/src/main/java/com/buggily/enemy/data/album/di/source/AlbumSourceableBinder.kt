package com.buggily.enemy.data.album.di.source

import com.buggily.enemy.data.album.source.AlbumSource
import com.buggily.enemy.data.album.source.AlbumSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AlbumSourceableBinder {

    @Binds
    fun binds(
        source: AlbumSource,
    ): AlbumSourceable
}
