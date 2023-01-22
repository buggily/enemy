package com.buggily.core.domain.di

import com.buggily.core.domain.GetInstant
import com.buggily.core.domain.GetLocalDateTime
import com.buggily.core.domain.GetTimeZone
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetLocalDateTimeProvider {

    @Provides
    fun provides(
        getInstant: GetInstant,
        getTimeZone: GetTimeZone,
    ): GetLocalDateTime = GetLocalDateTime(
        getInstant = getInstant,
        getTimeZone = getTimeZone,
    )
}
