package com.buggily.enemy.feature.orientation

import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OrientationViewModel : ViewModel() {

    private val _state: MutableStateFlow<OrientationState>
    val state: StateFlow<OrientationState> get() = _state

    val permission: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

    init {
        OrientationState.default.copy(
            grantState = OrientationState.GrantState.default.copy(
                onGrant = ::onGrant,
            ),
            pendState = OrientationState.PendState.default.copy(
                onPend = ::onPend,
            ),
            denyState = OrientationState.DenyState.default.copy(
                onDeny = ::onDeny,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    private fun onGrant() = setPermissionState(
        permissionState = OrientationState.PermissionState.Event.Grant(
            onGrant = ::resetPermissionState,
        ),
    )

    private fun onPend() = setPermissionState(
        permissionState = OrientationState.PermissionState.Event.Pend(
            onPend = ::resetPermissionState,
        ),
    )

    private fun onDeny() = setPermissionState(
        permissionState = OrientationState.PermissionState.Event.Deny(
            onDeny = ::resetPermissionState,
        ),
    )

    private fun resetPermissionState() = setPermissionState(
        permissionState = OrientationState.PermissionState.Default,
    )

    private fun setPermissionState(permissionState: OrientationState.PermissionState) =
        _state.update {
            it.copy(permissionState = permissionState)
        }
}
