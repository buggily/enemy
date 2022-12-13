package com.buggily.core.domain

import com.buggily.enemy.core.model.Duration
import java.util.Locale
import java.util.concurrent.TimeUnit

class GetDuration(
    private val locale: Locale,
) {

    operator fun invoke(duration: Long): Duration {
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes)
        val milliseconds: Long = TimeUnit.MILLISECONDS.toMillis(duration) - TimeUnit.SECONDS.toMillis(seconds)

        val text: String = String.format(
            locale = locale,
            format = "%d:%02d",
            minutes,
            seconds,
        )

        return Duration(
            milliseconds = milliseconds,
            seconds = seconds,
            minutes = minutes,
            text = text,
        )
    }
}
