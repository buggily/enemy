package com.buggily.enemy.core.domain.di

import com.buggily.enemy.core.domain.GetTimeZone
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTimeZoneProvider {

    @Provides
    fun provides(): GetTimeZone = GetTimeZone()
}
