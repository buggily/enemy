package com.buggily.enemy.domain.di.use.theme

import com.buggily.enemy.data.repository.theme.ThemeRepositable
import com.buggily.enemy.domain.map.theme.ThemeMapper
import com.buggily.enemy.domain.use.theme.SetTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SetThemeProvider {

    @Provides
    fun provides(
        repository: ThemeRepositable,
        mapper: ThemeMapper,
    ): SetTheme = SetTheme(
        repository = repository,
        mapper = mapper,
    )
}
