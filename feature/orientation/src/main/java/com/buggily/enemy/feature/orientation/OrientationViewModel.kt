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

    private fun onGrant() = _state.update {
        val permissionState = OrientationState.PermissionState.Event.Grant(
            onGrant = ::resetPermissionState,
        )

        it.copy(permissionState = permissionState)
    }

    private fun onPend() = _state.update {
        val permissionState = OrientationState.PermissionState.Event.Pend(
            onPend = ::resetPermissionState,
        )

        it.copy(permissionState = permissionState)
    }

    private fun onDeny() = _state.update {
        it.copy(statusState = OrientationState.StatusState.Deny)
    }

    private fun resetPermissionState() = _state.update {
        it.copy(permissionState = OrientationState.PermissionState.Default)
    }
}
