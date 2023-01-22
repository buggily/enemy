package com.buggily.core.domain

import kotlinx.datetime.TimeZone

class GetTimeZone {

    operator fun invoke(): TimeZone = TimeZone.currentSystemDefault()
}
