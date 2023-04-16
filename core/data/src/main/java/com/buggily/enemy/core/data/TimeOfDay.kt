package com.buggily.enemy.core.data

sealed class TimeOfDay {
    object Morning : TimeOfDay()
    object Afternoon : TimeOfDay()
    object Evening : TimeOfDay()
}
