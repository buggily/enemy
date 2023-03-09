package com.buggily.enemy.data.playlist.di.source

import com.buggily.enemy.data.playlist.source.PlaylistSource
import com.buggily.enemy.data.playlist.source.PlaylistSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlaylistSourceableBinder {

    @Binds
    fun binds(
        source: PlaylistSource,
    ): PlaylistSourceable
}
