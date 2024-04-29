package com.buggily.enemy.di.media

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object MediaSessionProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
        @ExoPlayerQualifier player: Player,
    ): MediaSession = MediaSession.Builder(
        context,
        player
    ).build()
}
