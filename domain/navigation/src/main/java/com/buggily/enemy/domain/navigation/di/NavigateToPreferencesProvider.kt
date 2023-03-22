package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateToPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigateToPreferencesProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateToPreferences = NavigateToPreferences(
        navigate = navigate,
    )
}
