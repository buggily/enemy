package com.buggily.enemy.feature.orientation

data class OrientationUiState(
    val permissionState: PermissionState,
) {

    sealed class PermissionState(
        open val onClick: (Int) -> Unit,
    ) {
        data class Default(
            override val onClick: (Int) -> Unit,
        ) : PermissionState(
            onClick = onClick,
        )

        data class Deny(
            override val onClick: (Int) -> Unit,
        )  : PermissionState(
            onClick = onClick,
        )

        companion object {
            val default: Default
                get() = Default(
                    onClick = {},
                )
        }
    }

    companion object {
        val default: OrientationUiState
            get() = OrientationUiState(
                permissionState = PermissionState.default,
            )
    }
}
