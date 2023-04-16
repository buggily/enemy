package com.buggily.enemy.data.theme.di

import com.buggily.enemy.data.theme.ThemeRepositable
import com.buggily.enemy.data.theme.ThemeRepository
import com.buggily.enemy.local.preferences.dynamic.LocalDynamicSourceable
import com.buggily.enemy.local.preferences.scheme.LocalSchemeSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ThemeRepositableProvider {

    @Provides
    fun provides(
        localSchemeSource: LocalSchemeSourceable,
        localDynamicSource: LocalDynamicSourceable,
    ): ThemeRepositable = ThemeRepository(
        localSchemeSource = localSchemeSource,
        localDynamicSource = localDynamicSource,
    )
}
