package com.buggily.enemy.core.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetDateTimeFormatterProvider {

    @Provides
    fun provides(): com.buggily.enemy.core.domain.GetDateTimeFormatter =
        com.buggily.enemy.core.domain.GetDateTimeFormatter()
}
