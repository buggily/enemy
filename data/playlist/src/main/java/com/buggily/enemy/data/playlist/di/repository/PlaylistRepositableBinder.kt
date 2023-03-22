package com.buggily.enemy.data.playlist.di.repository

import com.buggily.enemy.data.playlist.repository.PlaylistRepositable
import com.buggily.enemy.data.playlist.repository.PlaylistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlaylistRepositableBinder {

    @Binds
    fun binds(
        repository: PlaylistRepository,
    ): PlaylistRepositable
}
