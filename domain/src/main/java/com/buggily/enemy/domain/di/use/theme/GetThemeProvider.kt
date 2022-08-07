package com.buggily.enemy.domain.di.use.theme

import com.buggily.enemy.data.repository.theme.ThemeRepositable
import com.buggily.enemy.domain.map.theme.ThemeMapper
import com.buggily.enemy.domain.use.theme.GetTheme
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
        mapper: ThemeMapper,
    ): GetTheme = GetTheme(
        repository = repository,
        mapper = mapper,
    )
}
