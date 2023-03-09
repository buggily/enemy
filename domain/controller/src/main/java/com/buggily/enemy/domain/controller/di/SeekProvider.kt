package com.buggily.enemy.domain.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.domain.controller.Seek
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SeekProvider {

    @Provides
    fun provides(
        orchestrator: ControllerOrchestratable,
    ): Seek = Seek(
        orchestrator = orchestrator,
    )
}

