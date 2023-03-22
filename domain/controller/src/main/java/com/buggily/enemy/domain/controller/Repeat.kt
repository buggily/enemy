package com.buggily.enemy.domain.controller

import com.buggily.enemy.core.controller.ControllerOrchestratable

class Repeat(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke(
        repeatMode: Int,
    ) = orchestrator.repeat(
        repeatMode = repeatMode,
    )
}
