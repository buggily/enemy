package com.buggily.enemy.data.theme.di.repository

import com.buggily.enemy.data.theme.repository.ThemeRepository
import com.buggily.enemy.data.theme.source.dynamic.DynamicLocalSourceable
import com.buggily.enemy.data.theme.source.scheme.SchemeLocalSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThemeRepositoryProvider {

    @Provides
    fun provides(
        schemeSource: SchemeLocalSourceable,
        dynamicSource: DynamicLocalSourceable,
    ): ThemeRepository = ThemeRepository(
        schemeSource = schemeSource,
        dynamicSource = dynamicSource,
    )
}
