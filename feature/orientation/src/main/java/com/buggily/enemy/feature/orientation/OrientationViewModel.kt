package com.buggily.enemy.feature.orientation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OrientationViewModel : ViewModel() {

    private val _state: MutableStateFlow<OrientationState>
    val state: StateFlow<OrientationState> get() = _state

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
        permissionState = OrientationState.PermissionState.EventState.Grant(
            onGrant = ::resetPermissionState,
        ),
    )

    private fun onPend() = setPermissionState(
        permissionState = OrientationState.PermissionState.EventState.Pend(
            onPend = ::resetPermissionState,
        ),
    )

    private fun onDeny() = setPermissionState(
        permissionState = OrientationState.PermissionState.EventState.Deny(
            onDeny = ::resetPermissionState,
        ),
    )

    private fun resetPermissionState() = setPermissionState(
        permissionState = OrientationState.PermissionState.Default,
    )

    private fun setPermissionState(permissionState: OrientationState.PermissionState) = _state.update {
        it.copy(permissionState = permissionState)
    }
}
