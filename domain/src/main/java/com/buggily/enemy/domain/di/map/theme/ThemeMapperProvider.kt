package com.buggily.enemy.domain.di.map.theme

import com.buggily.enemy.domain.map.theme.ThemeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThemeMapperProvider {

    @Provides
    fun provides(): ThemeMapper = ThemeMapper()
}
