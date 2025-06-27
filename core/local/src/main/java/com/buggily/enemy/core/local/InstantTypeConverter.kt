package com.buggily.enemy.core.local

import androidx.room.TypeConverter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal class InstantTypeConverter {

    @TypeConverter
    fun to(epochMilliseconds: Long): Instant = Instant.fromEpochMilliseconds(epochMilliseconds)

    @TypeConverter
    fun from(instant: Instant): Long = instant.toEpochMilliseconds()
}
