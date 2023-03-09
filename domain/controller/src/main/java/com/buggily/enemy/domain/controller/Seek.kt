package com.buggily.enemy.domain.controller

import com.buggily.enemy.core.controller.ControllerOrchestratable

class Seek(
    private val orchestrator: ControllerOrchestratable,
) {

    operator fun invoke(
        milliseconds: Long,
    ) = orchestrator.seek(
        milliseconds = milliseconds,
    )
}
