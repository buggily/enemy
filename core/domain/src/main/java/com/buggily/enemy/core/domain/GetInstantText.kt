package com.buggily.enemy.core.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaLocalDateTime

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
