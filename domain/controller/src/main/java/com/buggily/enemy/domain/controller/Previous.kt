package com.buggily.enemy.domain.controller

import com.buggily.enemy.core.controller.ControllerOrchestratable

class Previous(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke() = orchestrator.previous()
}
