package com.buggily.enemy.data.di.source.theme

import com.buggily.enemy.data.source.theme.ThemeSource
import com.buggily.enemy.data.source.theme.ThemeSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ThemeSourceableBinder {

    @Binds
    fun binds(
        source: ThemeSource,
    ): ThemeSourceable
}
