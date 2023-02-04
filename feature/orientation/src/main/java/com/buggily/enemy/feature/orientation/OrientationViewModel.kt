package com.buggily.enemy.feature.orientation

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OrientationViewModel : ViewModel() {

    private val _state: MutableStateFlow<OrientationState> =
        MutableStateFlow(OrientationState.default)

    val state: StateFlow<OrientationState> get() = _state

    fun onResultCheck(permissionResult: Int) = onResultCheck(
        isGranted = permissionResult == PackageManager.PERMISSION_GRANTED,
    )

    private fun onResultCheck(isGranted: Boolean) {
        if (isGranted) onGrant() else onPend()
    }

    fun onResult(isGranted: Boolean) {
        if (isGranted) onGrant() else onDeny()
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

    private fun onDeny() = setStatusState(
        statusState = OrientationState.StatusState.Deny,
    )

    private fun setStatusState(
        statusState: OrientationState.StatusState,
    ) = _state.update {
        it.copy(statusState = statusState)
    }

    private fun resetPermissionState() = setPermissionState(
        permissionState = OrientationState.PermissionState.Default,
    )

    private fun setPermissionState(
        permissionState: OrientationState.PermissionState,
    ) = _state.update {
        it.copy(permissionState = permissionState)
    }
}
