package com.buggily.core.domain.di

import com.buggily.core.domain.GetInstantText
import com.buggily.core.domain.GetLocalDateTimeFromInstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter

@Module
@InstallIn(SingletonComponent::class)
object GetInstantTextProvider {

    @Provides
    fun provides(
        getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
        dateTimeFormatter: DateTimeFormatter,
    ): GetInstantText = GetInstantText(
        getLocalDateTimeFromInstant = getLocalDateTimeFromInstant,
        dateTimeFormatter = dateTimeFormatter,
    )
}
