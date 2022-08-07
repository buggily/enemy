package com.buggily.enemy.domain.use

import com.buggily.enemy.domain.track.Track
import java.util.Locale
import java.util.concurrent.TimeUnit

class GetDuration(
    private val locale: Locale,
) {

    operator fun invoke(duration: Long): Track.Duration {
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes)
        val milliseconds: Long = TimeUnit.MILLISECONDS.toMillis(duration) - TimeUnit.SECONDS.toMillis(seconds)

        val display: String = String.format(
            locale = locale,
            format = "%d:%02d",
            minutes,
            seconds,
        )

        return Track.Duration(
            identity = duration,
            milliseconds = milliseconds,
            seconds = seconds,
            minutes = minutes,
            display = display,
        )
    }
}