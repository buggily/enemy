package com.buggily.enemy.domain.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.domain.controller.Repeat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepeatProvider {

    @Provides
    fun provides(
        orchestrator: ControllerOrchestratable,
    ): Repeat = Repeat(
        orchestrator = orchestrator,
    )
}

