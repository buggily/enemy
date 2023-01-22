package com.buggily.core.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class GetLocalDateTime(
    private val getInstant: GetInstant,
    private val getTimeZone: GetTimeZone,
) {

    operator fun invoke(): LocalDateTime = getInstant().toLocalDateTime(getTimeZone())
}
