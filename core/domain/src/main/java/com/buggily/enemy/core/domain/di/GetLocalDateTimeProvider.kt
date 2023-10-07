package com.buggily.enemy.core.domain.di

import com.buggily.enemy.core.domain.GetInstant
import com.buggily.enemy.core.domain.GetLocalDateTime
import com.buggily.enemy.core.domain.GetLocalDateTimeFromInstant
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
        getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
    ): GetLocalDateTime = GetLocalDateTime(
        getInstant = getInstant,
        getLocalDateTimeFromInstant = getLocalDateTimeFromInstant,
    )
}
