package com.buggily.enemy.core.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ControllerOrchestratorProvider {

    @Provides
    @Singleton
    fun provides(): ControllerOrchestrator = ControllerOrchestrator()
}
