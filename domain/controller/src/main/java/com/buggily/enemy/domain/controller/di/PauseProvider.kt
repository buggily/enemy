package com.buggily.enemy.domain.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.domain.controller.Pause
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PauseProvider {

    @Provides
    fun provides(
        orchestrator: ControllerOrchestratable,
    ): Pause = Pause(
        orchestrator = orchestrator,
    )
}

