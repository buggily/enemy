package com.buggily.core.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class GetLocalDateTimeFromInstant(
    private val getTimeZone: GetTimeZone,
) {

    operator fun invoke(
        instant: Instant,
    ): LocalDateTime = instant.toLocalDateTime(
        timeZone = getTimeZone(),
    )
}
