package com.buggily.enemy.domain.theme.di

import com.buggily.enemy.data.theme.repository.ThemeRepositable
import com.buggily.enemy.domain.theme.GetTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetThemeProvider {

    @Provides
    fun provides(
        repository: ThemeRepositable,
    ): GetTheme = GetTheme(
        repository = repository,
    )
}
