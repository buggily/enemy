package com.buggily.core.domain.di

import com.buggily.core.domain.GetLocalTime
import com.buggily.core.domain.GetTimeOfDay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTimeOfDayProvider {

    @Provides
    fun provide(
        getLocalTime: GetLocalTime,
    ): GetTimeOfDay = GetTimeOfDay(
        getLocalTime = getLocalTime,
    )
}
