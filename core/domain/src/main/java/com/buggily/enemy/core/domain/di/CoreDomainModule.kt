package com.buggily.enemy.core.domain.di

import com.buggily.enemy.core.domain.GetDateTimeFormatter
import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.core.domain.GetInstant
import com.buggily.enemy.core.domain.GetInstantText
import com.buggily.enemy.core.domain.GetLocalDateTime
import com.buggily.enemy.core.domain.GetLocalDateTimeFromInstant
import com.buggily.enemy.core.domain.GetLocalTime
import com.buggily.enemy.core.domain.GetTimeOfDay
import com.buggily.enemy.core.domain.GetTimeZone
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object CoreDomainModule {

    @Provides
    fun providesGetDateTimeFormatter(): GetDateTimeFormatter = GetDateTimeFormatter()

    @Provides
    fun providesGetInstant(): GetInstant = GetInstant()

    @Provides
    fun providesGetDurationFromLong(): GetDuration = GetDuration()

    @Provides
    fun providesGetDurationText(
        locale: Locale,
        getDuration: GetDuration,
    ): GetDurationText = GetDurationText(
        locale = locale,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetInstantText(
        getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
        getDateTimeFormatter: GetDateTimeFormatter,
    ): GetInstantText = GetInstantText(
        getLocalDateTimeFromInstant = getLocalDateTimeFromInstant,
        getDateTimeFormatter = getDateTimeFormatter,
    )

    @Provides
    fun providesGetLocalDateTimeFromInstant(
        getTimeZone: GetTimeZone,
    ): GetLocalDateTimeFromInstant = GetLocalDateTimeFromInstant(
        getTimeZone = getTimeZone,
    )

    @Provides
    fun providesGetLocalDateTime(
        getInstant: GetInstant,
        getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
    ): GetLocalDateTime = GetLocalDateTime(
        getInstant = getInstant,
        getLocalDateTimeFromInstant = getLocalDateTimeFromInstant,
    )

    @Provides
    fun providesGetLocalTime(
        getLocalDateTime: GetLocalDateTime,
    ): GetLocalTime = GetLocalTime(
        getLocalDateTime = getLocalDateTime,
    )

    @Provides
    fun providesGetTimeOfDay(
        getLocalTime: GetLocalTime,
    ): GetTimeOfDay = GetTimeOfDay(
        getLocalTime = getLocalTime,
    )

    @Provides
    fun providesGetTimeZone(): GetTimeZone = GetTimeZone()

    @Provides
    fun providesLocale(): Locale = Locale.getDefault()
}
