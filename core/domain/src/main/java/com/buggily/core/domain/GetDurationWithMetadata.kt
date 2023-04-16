package com.buggily.core.domain

import com.buggily.enemy.core.data.DurationWithMetadata
import com.buggily.enemy.core.ext.isNonNegative
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GetDurationWithMetadata(
    private val locale: Locale,
) {

    operator fun invoke(milliseconds: Long): DurationWithMetadata {
        val duration: Duration = milliseconds.toDuration(DurationUnit.MILLISECONDS)
        return invoke(duration)
    }

    operator fun invoke(duration: Duration): DurationWithMetadata {
        require(duration.isFinite())
        require(duration.isNonNegative())

        val minutesDuration: Duration = duration.inWholeMinutes.toDuration(
            unit = DurationUnit.MINUTES,
        )

        val secondsDuration: Duration = duration.inWholeSeconds.toDuration(
            unit = DurationUnit.SECONDS,
        )

        val remainingSecondsDuration: Duration = secondsDuration - minutesDuration
        val minutes: Long = minutesDuration.inWholeMinutes
        val seconds: Long = remainingSecondsDuration.inWholeSeconds

        val text: String = String.format(
            locale = locale,
            format = "%d:%02d",
            minutes,
            seconds,
        )

        return DurationWithMetadata(
            text = text,
            value = duration,
        )
    }
}
