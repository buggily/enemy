package com.buggily.enemy

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import com.buggily.enemy.core.ext.isPositive
import com.buggily.enemy.domain.resume.GetResume
import com.buggily.enemy.domain.resume.ResumeUi
import com.buggily.enemy.domain.resume.SetResumeIdAndIndex
import com.buggily.enemy.domain.resume.SetResumePosition
import com.buggily.enemy.domain.resume.UseResume
import com.buggily.enemy.domain.track.IncrementTrackPlaysById
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/*
    While enemy does not fully implement the `MediaLibraryService` and is better suited for a
    `MediaSessionService`, playback resumption through the Android System UI playback resumption
    media button is only available through a `MediaLibraryService`. The app may fully
    implement the `MediaLibraryService` in the future.

    (https://developer.android.com/media/media3/session/background-playback#resumption)
*/

@AndroidEntryPoint
class EnemyMediaService : MediaLibraryService() {

    @Inject
    lateinit var getResume: GetResume

    @Inject
    lateinit var useResume: UseResume

    @Inject
    lateinit var setResumeIdAndIndex: SetResumeIdAndIndex

    @Inject
    lateinit var setResumePosition: SetResumePosition

    @Inject
    lateinit var incrementTrackPlaysById: IncrementTrackPlaysById

    private val service = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO.plus(service))

    private var mediaLibrarySession: MediaLibrarySession? = null
    private val playerListener: Player.Listener = object : Player.Listener {

        override fun onEvents(
            player: Player,
            events: Player.Events,
        ) {
            super.onEvents(player, events)

            if (!events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)) return
            val id: Long = player.currentMediaItem?.mediaId?.toLongOrNull() ?: return
            val index: Int = player.currentMediaItemIndex

            serviceScope.launch {
                incrementTrackPlaysById(id)
                setResumeIdAndIndex(id, index)
            }
        }
    }

    @UnstableApi
    private val callback: MediaLibrarySession.Callback = object : MediaLibrarySession.Callback {

        override fun onPlaybackResumption(
            mediaSession: MediaSession,
            controller: MediaSession.ControllerInfo,
        ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
            val future: SettableFuture<MediaSession.MediaItemsWithStartPosition> =
                SettableFuture.create()

            val resume: ResumeUi = runBlocking {
                getResume()
            } ?: return Futures.immediateCancelledFuture()

            serviceScope.launch {
                MediaSession.MediaItemsWithStartPosition(
                    useResume(resume),
                    resume.source.index,
                    resume.position
                ).let { future.set(it) }
            }

            return future
        }
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo,
    ): MediaLibrarySession? = mediaLibrarySession

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        val player: Player = ExoPlayer.Builder(this)
            .setHandleAudioBecomingNoisy(true)
            .build()

        mediaLibrarySession = MediaLibrarySession.Builder(
            this,
            player,
            callback
        ).build()

        mediaLibrarySession?.player?.run {
            removeListener(playerListener)
            addListener(playerListener)
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        mediaLibrarySession?.player?.run {
            if (playWhenReady) return
            if (mediaItemCount.isPositive) return
            if (playbackState == Player.STATE_ENDED) stopSelf()
        }
    }

    override fun onDestroy() {
        service.cancel()

        mediaLibrarySession?.player?.run {
            serviceScope.launch { setResumePosition(currentPosition) }

            removeListener(playerListener)
            release()
        }

        mediaLibrarySession?.release()
        mediaLibrarySession = null

        super.onDestroy()
    }
}
