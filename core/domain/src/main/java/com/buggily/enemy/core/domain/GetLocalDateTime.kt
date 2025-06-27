package com.buggily.enemy.core.domain

import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetLocalDateTime(
    private val getInstant: GetInstant,
    private val getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
) {

    operator fun invoke(): LocalDateTime = getLocalDateTimeFromInstant(
        instant = getInstant(),
    )
}
