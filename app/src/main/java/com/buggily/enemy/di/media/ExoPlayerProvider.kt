package com.buggily.enemy.di.media

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ExoPlayerProvider {

    @Provides
    @ExoPlayerQualifier
    fun provides(
        @ApplicationContext context: Context,
    ): Player = ExoPlayer.Builder(context).build()
}
