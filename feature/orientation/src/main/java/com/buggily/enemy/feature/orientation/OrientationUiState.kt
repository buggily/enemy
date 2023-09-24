package com.buggily.enemy.feature.orientation

data class OrientationUiState(
    val permissionState: PermissionState,
) {

    data class PermissionState(
        val onClick: (Int) -> Unit
    )
}
