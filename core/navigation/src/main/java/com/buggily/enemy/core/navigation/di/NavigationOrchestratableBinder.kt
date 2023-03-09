package com.buggily.enemy.core.navigation.di

import com.buggily.enemy.core.navigation.NavigationOrchestratable
import com.buggily.enemy.core.navigation.NavigationOrchestrator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationOrchestratableBinder {

    @Binds
    fun binds(
        orchestrator: NavigationOrchestrator,
    ): NavigationOrchestratable
}
