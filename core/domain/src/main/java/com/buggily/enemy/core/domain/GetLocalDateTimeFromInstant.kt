package com.buggily.enemy.core.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class GetLocalDateTimeFromInstant(
    private val getTimeZone: GetTimeZone,
) {

    operator fun invoke(
        instant: Instant,
    ): LocalDateTime = instant.toLocalDateTime(
        timeZone = getTimeZone(),
    )
}
