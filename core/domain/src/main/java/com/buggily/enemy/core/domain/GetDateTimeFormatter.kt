package com.buggily.enemy.core.domain

import java.time.format.DateTimeFormatter

class GetDateTimeFormatter {
    operator fun invoke(): DateTimeFormatter = DateTimeFormatter.ISO_DATE
}