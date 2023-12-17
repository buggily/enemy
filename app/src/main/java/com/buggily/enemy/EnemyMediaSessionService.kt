package com.buggily.enemy

import android.content.Intent
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.buggily.enemy.core.ext.isPositive
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnemyMediaSessionService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo,
    ): MediaSession = mediaSession


    override fun onTaskRemoved(rootIntent: Intent?) = with (mediaSession.player) {
        if (playWhenReady) return
        if (mediaItemCount.isPositive) return

        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    private fun release() = with(mediaSession) {
        player.release()
        release()
    }
}
