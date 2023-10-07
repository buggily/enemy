package com.buggily.enemy.core.domain.di

import com.buggily.enemy.core.domain.GetLocalDateTime
import com.buggily.enemy.core.domain.GetLocalTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetLocalTimeProvider {

    @Provides
    fun provides(
        getLocalDateTime: GetLocalDateTime,
    ): GetLocalTime = GetLocalTime(
        getLocalDateTime = getLocalDateTime,
    )
}
