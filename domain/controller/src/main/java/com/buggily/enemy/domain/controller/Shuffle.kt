package com.buggily.enemy.domain.controller

import com.buggily.enemy.core.controller.ControllerOrchestratable

class Shuffle(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke(
        shuffleMode: Boolean,
    ) = orchestrator.shuffle(
        shuffleMode = shuffleMode,
    )
}
