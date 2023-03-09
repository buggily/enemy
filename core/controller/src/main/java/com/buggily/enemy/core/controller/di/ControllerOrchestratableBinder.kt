package com.buggily.enemy.core.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.core.controller.ControllerOrchestrator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ControllerOrchestratableBinder {

    @Binds
    fun binds(
        orchestrator: ControllerOrchestrator,
    ): ControllerOrchestratable
}
