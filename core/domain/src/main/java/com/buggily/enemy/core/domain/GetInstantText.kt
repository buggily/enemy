package com.buggily.enemy.core.domain

import kotlinx.datetime.toJavaLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class GetInstantText(
    private val getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
    private val getDateTimeFormatter: GetDateTimeFormatter,
) {

    operator fun invoke(
        instant: Instant,
    ): String = getLocalDateTimeFromInstant(
        instant = instant,
    ).toJavaLocalDateTime().format(getDateTimeFormatter())
}
