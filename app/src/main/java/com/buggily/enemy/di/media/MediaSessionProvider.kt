package com.buggily.enemy.di.media

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaSessionProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
        @ExoPlayerQualifier player: Player,
    ): MediaSession {
        val callback: MediaSession.Callback = object : MediaSession.Callback {
            override fun onAddMediaItems(
                mediaSession: MediaSession,
                controller: MediaSession.ControllerInfo,
                mediaItems: MutableList<MediaItem>
            ): ListenableFuture<MutableList<MediaItem>> = mediaItems.map {
                val mediaUri: Uri? = it.requestMetadata.mediaUri
                val mediaId: String? = mediaUri?.let { ContentUris.parseId(mediaUri) }?.toString()

                it.buildUpon()
                    .apply { mediaId?.let { setMediaId(mediaId) } }
                    .setUri(mediaUri)
                    .build()
            }.let { Futures.immediateFuture(it.toMutableList()) }
        }

        return MediaSession.Builder(
            context,
            player
        )
            .setCallback(callback)
            .build()
    }
}
