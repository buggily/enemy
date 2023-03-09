package com.buggily.enemy.domain.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.domain.controller.Shuffle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShuffleProvider {

    @Provides
    fun provides(
        orchestrator: ControllerOrchestratable,
    ): Shuffle = Shuffle(
        orchestrator = orchestrator,
    )
}

