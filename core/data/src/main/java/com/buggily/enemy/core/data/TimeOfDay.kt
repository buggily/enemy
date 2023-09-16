package com.buggily.enemy.core.data

sealed interface TimeOfDay {
    data object Morning : TimeOfDay
    data object Afternoon : TimeOfDay
    data object Evening : TimeOfDay
}
