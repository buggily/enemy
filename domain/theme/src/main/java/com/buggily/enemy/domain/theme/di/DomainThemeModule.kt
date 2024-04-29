package com.buggily.enemy.domain.theme.di

import com.buggily.enemy.data.theme.ThemeRepositable
import com.buggily.enemy.domain.theme.GetTheme
import com.buggily.enemy.domain.theme.SetTheme
import com.buggily.enemy.domain.theme.SetThemeDynamic
import com.buggily.enemy.domain.theme.SetThemeScheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainThemeModule {

    @Provides
    fun providesGetTheme(
        themeRepository: ThemeRepositable,
    ): GetTheme = GetTheme(
        themeRepository = themeRepository,
    )

    @Provides
    fun providesSetTheme(
        themeRepository: ThemeRepositable,
    ): SetTheme = SetTheme(
        themeRepository = themeRepository,
    )

    @Provides
    fun providesSetThemeDynamic(
        themeRepository: ThemeRepositable,
    ): SetThemeDynamic = SetThemeDynamic(
        themeRepository = themeRepository,
    )

    @Provides
    fun providesSetThemeScheme(
        themeRepository: ThemeRepositable,
    ): SetThemeScheme = SetThemeScheme(
        themeRepository = themeRepository,
    )
}
