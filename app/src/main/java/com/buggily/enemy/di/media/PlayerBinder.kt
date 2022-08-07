package com.buggily.enemy.di.media

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PlayerBinder {

    @Binds
    fun binds(
        player: ExoPlayer,
    ): Player
}
