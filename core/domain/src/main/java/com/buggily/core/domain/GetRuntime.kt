package com.buggily.core.domain

import com.buggily.enemy.core.ext.isNonNegative
import com.buggily.enemy.core.model.Runtime
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GetRuntime(
    private val locale: Locale,
) {

    operator fun invoke(milliseconds: Long): Runtime {
        val duration: Duration = milliseconds.toDuration(DurationUnit.MILLISECONDS)
        return invoke(duration)
    }

    operator fun invoke(duration: Duration): Runtime {
        require(duration.isFinite())
        require(duration.isNonNegative())

        val minutesDuration: Duration = duration.inWholeMinutes.toDuration(DurationUnit.MINUTES)
        val secondsDuration: Duration = duration.inWholeSeconds.toDuration(DurationUnit.SECONDS)
        val remainingSecondsDuration: Duration = secondsDuration - minutesDuration

        val minutes: Long = minutesDuration.inWholeMinutes
        val seconds: Long = remainingSecondsDuration.inWholeSeconds

        val text: String = String.format(
            locale = locale,
            format = "%d:%02d",
            minutes,
            seconds,
        )

        return Runtime(
            text = text,
            duration = duration,
        )
    }
}
