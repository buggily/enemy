package com.buggily.enemy.feature.orientation

sealed class OrientationEventState {
    object ReadPermission : OrientationEventState()
}
