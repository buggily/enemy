package com.buggily.enemy.data.di.source.album

import com.buggily.enemy.data.source.album.AlbumSource
import com.buggily.enemy.data.source.album.AlbumSourceable
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
