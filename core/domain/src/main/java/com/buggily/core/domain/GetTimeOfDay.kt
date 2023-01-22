package com.buggily.core.domain

import com.buggily.enemy.core.model.TimeOfDay

class GetTimeOfDay(
    private val getLocalTime: GetLocalTime,
) {

    operator fun invoke(): TimeOfDay = when (getLocalTime().hour) {
        in 0 until 12 -> TimeOfDay.Morning
        in 12 until 18 -> TimeOfDay.Afternoon
        else -> TimeOfDay.Evening
    }
}
