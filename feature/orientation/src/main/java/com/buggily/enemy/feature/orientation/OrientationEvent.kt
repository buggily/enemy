package com.buggily.enemy.feature.orientation

sealed interface OrientationEvent {
    data object ReadPermission : OrientationEvent
}
