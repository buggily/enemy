package com.buggily.core.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

class GetInstantText(
    private val getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
    private val dateTimeFormatter: DateTimeFormatter,
) {

    operator fun invoke(
        instant: Instant,
    ): String = getLocalDateTimeFromInstant(
        instant = instant,
    ).toJavaLocalDateTime().format(dateTimeFormatter)
}
