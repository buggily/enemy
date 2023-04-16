package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.core.navigation.NavigationOrchestratable
import com.buggily.enemy.domain.navigation.Navigate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NavigateProvider {

    @Provides
    fun provides(
        orchestrator: NavigationOrchestratable,
    ): Navigate = Navigate(
        orchestrator = orchestrator,
    )
}
