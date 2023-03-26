package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateToOrientation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigateToOrientationProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateToOrientation = NavigateToOrientation(
        navigate = navigate,
    )
}