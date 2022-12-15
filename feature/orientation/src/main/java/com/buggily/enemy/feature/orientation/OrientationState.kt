package com.buggily.enemy.feature.orientation

data class OrientationState(
    val grantState: GrantState,
    val pendState: PendState,
    val denyState: DenyState,
    val permissionState: PermissionState,
) {

    data class GrantState(
        val onGrant: () -> Unit,
    ) {

        companion object {
            val default: GrantState
                get() = GrantState {}
        }
    }

    data class PendState(
        val onPend: () -> Unit,
    ) {

        companion object {
            val default: PendState
                get() = PendState {}
        }
    }

    data class DenyState(
        val onDeny: () -> Unit,
    ) {

        companion object {
            val default: DenyState
                get() = DenyState {}
        }
    }

    data class AlbumsState(
        val onHomeClick: () -> Unit,
    )

    sealed class PermissionState {

        object Default : PermissionState()

        sealed class EventState(
            val onEvent: () -> Unit,
        ) : PermissionState() {

            data class Grant(
                val onGrant: () -> Unit,
            ) : EventState(
                onEvent = onGrant,
            )

            data class Pend(
                val onPend: () -> Unit,
            ) : EventState(
                onEvent = onPend,
            )

            data class Deny(
                val onDeny: () -> Unit,
            ) : EventState(
                onEvent = onDeny,
            )
        }
    }

    companion object {
        val default: OrientationState
            get() = OrientationState(
                grantState = GrantState.default,
                pendState = PendState.default,
                denyState = DenyState.default,
                permissionState = PermissionState.Default,
            )
    }
}
