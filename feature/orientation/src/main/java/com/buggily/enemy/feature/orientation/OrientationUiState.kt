package com.buggily.enemy.feature.orientation

data class OrientationUiState(
    val permissionState: PermissionState,
) {

    sealed interface PermissionState {

        val onClick: (Int) -> Unit

        data class Default(
            override val onClick: (Int) -> Unit,
        ) : PermissionState

        data class Deny(
            override val onClick: (Int) -> Unit,
        )  : PermissionState
    }
}
