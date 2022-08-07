package com.buggily.enemy.data.di.repository.theme

import com.buggily.enemy.data.repository.theme.ThemeRepository
import com.buggily.enemy.data.source.theme.ThemeSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThemeRepositoryProvider {

    @Provides
    fun provides(
        source: ThemeSourceable,
    ): ThemeRepository = ThemeRepository(
        source = source,
    )
}
