package com.buggily.enemy.domain.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.domain.controller.Previous
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object PreviousProvider {

    @Provides
    fun provides(
        orchestrator: ControllerOrchestratable,
    ): Previous = Previous(
        orchestrator = orchestrator,
    )
}

