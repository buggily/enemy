package com.buggily.enemy.feature.orientation

sealed interface OrientationEventState {
    data object ReadPermission : OrientationEventState
}
