package com.buggily.enemy.core.navigation.di

import com.buggily.enemy.core.navigation.NavigationOrchestrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationOrchestratorProvider {

    @Provides
    @Singleton
    fun provides(): NavigationOrchestrator = NavigationOrchestrator()
}
