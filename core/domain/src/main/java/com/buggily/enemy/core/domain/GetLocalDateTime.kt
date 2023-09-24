package com.buggily.enemy.core.domain

import kotlinx.datetime.LocalDateTime

class GetLocalDateTime(
    private val getInstant: GetInstant,
    private val getLocalDateTimeFromInstant: GetLocalDateTimeFromInstant,
) {

    operator fun invoke(): LocalDateTime = getLocalDateTimeFromInstant(
        instant = getInstant(),
    )
}
