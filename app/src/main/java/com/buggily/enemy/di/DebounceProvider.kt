package com.buggily.enemy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DebounceProvider {

    @Provides
    @DebounceQualifier
    fun provides(): Long = 1000
}
