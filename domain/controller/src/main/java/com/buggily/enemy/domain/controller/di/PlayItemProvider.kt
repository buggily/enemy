package com.buggily.enemy.domain.controller.di

import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.domain.controller.PlayItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlayItemProvider {

    @Provides
    fun provides(
        orchestrator: ControllerOrchestratable,
    ): PlayItem = PlayItem(
        orchestrator = orchestrator,
    )
}

