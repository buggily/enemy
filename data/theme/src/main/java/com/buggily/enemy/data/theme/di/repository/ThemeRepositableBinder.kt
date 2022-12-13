package com.buggily.enemy.data.theme.di.repository

import com.buggily.enemy.data.theme.repository.ThemeRepositable
import com.buggily.enemy.data.theme.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ThemeRepositableBinder {

    @Binds
    fun binds(
        repository: ThemeRepository,
    ): ThemeRepositable
}
