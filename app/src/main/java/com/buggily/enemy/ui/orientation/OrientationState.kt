package com.buggily.enemy.ui.orientation

data class OrientationState(
    val state: State,
    val permissionState: PermissionState,
) {

    data class State(
        val grantState: GrantState,
        val pendState: PendState,
        val denyState: DenyState,
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

        companion object {
            val default: State
                get() = State(
                    grantState = GrantState.default,
                    pendState = PendState.default,
                    denyState = DenyState.default,
                )
        }
    }

    data class HomeState(
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
                state = State.default,
                permissionState = PermissionState.Default,
            )
    }
}
