package com.buggily.enemy.core.local

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverter {

    @TypeConverter
    fun to(
        epochMilliseconds: Long,
    ): Instant = Instant.fromEpochMilliseconds(
        epochMilliseconds = epochMilliseconds,
    )

    @TypeConverter
    fun from(
        instant: Instant,
    ): Long = instant.toEpochMilliseconds()
}
