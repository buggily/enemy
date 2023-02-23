package com.buggily.enemy.feature.orientation

data class OrientationState(
    val permissionState: PermissionState,
    val permissionEventState: PermissionEventState,
) {
    data class AlbumsState(
        val onGrant: () -> Unit,
    )

    sealed class PermissionState {
        object Default : PermissionState()
        object Deny  : PermissionState()
    }

    sealed class PermissionEventState {

        object Default : PermissionEventState()

        sealed class Event(
            val onEvent: () -> Unit,
        ) : PermissionEventState() {

            data class Grant(
                val onGrant: () -> Unit,
            ) : Event(
                onEvent = onGrant,
            )

            data class Pend(
                val onPend: () -> Unit,
            ) : Event(
                onEvent = onPend,
            )
        }
    }

    companion object {
        val default: OrientationState
            get() = OrientationState(
                permissionState = PermissionState.Default,
                permissionEventState = PermissionEventState.Default,
            )
    }
}
