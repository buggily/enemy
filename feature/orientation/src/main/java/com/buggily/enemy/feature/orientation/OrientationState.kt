package com.buggily.enemy.feature.orientation

data class OrientationState(
    val statusState: StatusState,
    val permissionState: PermissionState,
) {
    data class AlbumsState(
        val onGrant: () -> Unit,
    )

    sealed class StatusState {
        object Default : StatusState()
        object Deny  : StatusState()
    }

    sealed class PermissionState {

        object Default : PermissionState()

        sealed class Event(
            val onEvent: () -> Unit,
        ) : PermissionState() {

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
                statusState = StatusState.Default,
                permissionState = PermissionState.Default,
            )
    }
}
