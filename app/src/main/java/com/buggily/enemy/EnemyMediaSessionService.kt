package com.buggily.enemy

import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.buggily.enemy.core.ext.isPositive
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.domain.track.GetTrackById
import com.buggily.enemy.domain.track.IncrementTrackPlaysById
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EnemyMediaSessionService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var incrementTrackPlaysById: IncrementTrackPlaysById

    @Inject
    lateinit var getTrackById: GetTrackById

    private val service = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO.plus(service))

    private val playerListener: Player.Listener = object : Player.Listener {

        override fun onMediaItemTransition(
            mediaItem: MediaItem?,
            reason: Int,
        ) {
            super.onMediaItemTransition(
                mediaItem,
                reason,
            )

            mediaItem?.mediaId?.toLongOrNull()?.let {
                serviceScope.launch {
                    val track: Track = getTrackById(it) ?: return@launch
                    incrementTrackPlaysById(track.id)
                }
            }
        }
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo,
    ): MediaSession = mediaSession

    override fun onCreate() {
        super.onCreate()

        with(mediaSession.player) {
            removeListener(playerListener)
            addListener(playerListener)
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) = with(mediaSession.player) {
        if (playWhenReady) return
        if (mediaItemCount.isPositive) return

        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        service.cancel()

        with(mediaSession) {
            player.removeListener(playerListener)
            player.release()
            release()
        }
    }
}
