package com.buggily.core.domain

import kotlinx.datetime.LocalTime

class GetLocalTime(
    private val getLocalDateTime: GetLocalDateTime,
) {

    operator fun invoke(): LocalTime = getLocalDateTime().time
}
