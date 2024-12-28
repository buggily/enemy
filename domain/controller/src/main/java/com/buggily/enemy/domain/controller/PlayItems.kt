package com.buggily.enemy.domain.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.controller.ControllerOrchestratable

class PlayItems(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke(
        index: Int,
        items: List<MediaItem>,
    ) = orchestrator.playItems(
        index = index,
        items = items,
    )
}
