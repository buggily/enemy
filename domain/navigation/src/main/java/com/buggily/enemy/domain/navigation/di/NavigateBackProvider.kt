package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateBack
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigateBackProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateBack = NavigateBack(
        navigate = navigate,
    )
}
