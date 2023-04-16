package com.buggily.enemy.core.local

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

internal class InstantConverter {

    @TypeConverter
    fun to(epochMilliseconds: Long): Instant = Instant.fromEpochMilliseconds(epochMilliseconds)

    @TypeConverter
    fun from(instant: Instant): Long = instant.toEpochMilliseconds()
}
