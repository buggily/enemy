package com.buggily.enemy.data.playlist.di.source

import com.buggily.enemy.data.playlist.source.PlaylistLocalSource
import com.buggily.enemy.data.playlist.source.PlaylistLocalSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlaylistLocalSourceableBinder {

    @Binds
    fun binds(
        source: PlaylistLocalSource,
    ): PlaylistLocalSourceable
}
