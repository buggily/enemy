package com.buggily.enemy.core.domain

import com.buggily.enemy.core.ext.isNonNegative
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GetDurationText(
    private val locale: Locale,
    private val getDuration: GetDuration,
) {

    operator fun invoke(
        duration: Long,
    ): String = getDuration(
        duration = duration,
    ).let { invoke(it) }

    operator fun invoke(
        duration: Long,
        unit: DurationUnit,
    ): String = getDuration(
        duration = duration,
        unit = unit,
    ).let { invoke(it) }

    operator fun invoke(duration: Duration): String {
        require(duration.isNonNegative())
        require(duration.isFinite())

        val minutesDuration: Duration = duration.inWholeMinutes.toDuration(
            unit = DurationUnit.MINUTES,
        )

        val secondsDuration: Duration = duration.inWholeSeconds.toDuration(
            unit = DurationUnit.SECONDS,
        )

        val remainingSecondsDuration: Duration = secondsDuration - minutesDuration
        val minutes: Long = minutesDuration.inWholeMinutes
        val seconds: Long = remainingSecondsDuration.inWholeSeconds

        return String.format(
            locale = locale,
            format = "%d:%02d",
            minutes,
            seconds,
        )
    }
}
