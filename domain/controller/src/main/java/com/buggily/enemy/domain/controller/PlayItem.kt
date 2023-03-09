package com.buggily.enemy.domain.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.controller.ControllerOrchestratable

class PlayItem(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke(
        item: MediaItem,
    ) = orchestrator.playItem(
        item = item,
    )
}
