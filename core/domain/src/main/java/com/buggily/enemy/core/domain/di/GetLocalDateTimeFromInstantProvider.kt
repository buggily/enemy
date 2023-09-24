package com.buggily.enemy.core.domain.di

import com.buggily.core.domain.GetLocalDateTimeFromInstant
import com.buggily.core.domain.GetTimeZone
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetLocalDateTimeFromInstantProvider {

    @Provides
    fun provides(
        getTimeZone: GetTimeZone,
    ): GetLocalDateTimeFromInstant = GetLocalDateTimeFromInstant(
        getTimeZone = getTimeZone,
    )
}
