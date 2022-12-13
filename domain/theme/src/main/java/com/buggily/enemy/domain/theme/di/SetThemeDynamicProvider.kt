package com.buggily.enemy.domain.theme.di

import com.buggily.enemy.data.theme.repository.ThemeRepositable
import com.buggily.enemy.domain.theme.SetThemeDynamic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SetThemeDynamicProvider {

    @Provides
    fun provides(
        repository: ThemeRepositable,
    ): SetThemeDynamic = SetThemeDynamic(
        repository = repository,
    )
}

