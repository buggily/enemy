package com.buggily.enemy.domain.controller

import com.buggily.enemy.core.controller.ControllerOrchestratable

class Play(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke() = orchestrator.play()
}
