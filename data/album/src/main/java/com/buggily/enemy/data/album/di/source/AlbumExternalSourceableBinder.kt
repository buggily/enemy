package com.buggily.enemy.data.album.di.source

import com.buggily.enemy.data.album.source.AlbumExternalSource
import com.buggily.enemy.data.album.source.AlbumExternalSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AlbumExternalSourceableBinder {

    @Binds
    fun binds(
        source: AlbumExternalSource,
    ): AlbumExternalSourceable
}
