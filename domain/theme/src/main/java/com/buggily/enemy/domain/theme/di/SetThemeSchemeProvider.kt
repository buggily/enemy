package com.buggily.enemy.domain.theme.di

import com.buggily.enemy.data.theme.repository.ThemeRepositable
import com.buggily.enemy.domain.theme.SetThemeScheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SetThemeSchemeProvider {

    @Provides
    fun provides(
        repository: ThemeRepositable,
    ): SetThemeScheme = SetThemeScheme(
        repository = repository,
    )
}
