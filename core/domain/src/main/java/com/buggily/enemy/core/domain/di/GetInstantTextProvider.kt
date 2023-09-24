package com.buggily.enemy.core.domain.di

import com.buggily.enemy.core.domain.GetDateTimeFormatter
import com.buggily.core.domain.GetInstantText
import com.buggily.core.domain.GetLocalDateTimeFromInstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetInstantTextProvider {

    @Provides
    fun provides(
        getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
        getDateTimeFormatter: com.buggily.enemy.core.domain.GetDateTimeFormatter,
    ): GetInstantText = GetInstantText(
        getLocalDateTimeFromInstant = getLocalDateTimeFromInstant,
        getDateTimeFormatter = getDateTimeFormatter,
    )
}
