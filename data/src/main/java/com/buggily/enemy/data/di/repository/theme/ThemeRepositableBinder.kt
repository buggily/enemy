package com.buggily.enemy.data.di.repository.theme

import com.buggily.enemy.data.repository.theme.ThemeRepositable
import com.buggily.enemy.data.repository.theme.ThemeRepository
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
