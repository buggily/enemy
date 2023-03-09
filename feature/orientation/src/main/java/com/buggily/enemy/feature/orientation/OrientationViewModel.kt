package com.buggily.enemy.feature.orientation

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import com.buggily.enemy.domain.navigation.NavigateFromOrientationToBrowse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrientationViewModel @Inject constructor(
    private val navigateFromOrientationToBrowse: NavigateFromOrientationToBrowse,
) : ViewModel() {

    private val _uiState: MutableStateFlow<OrientationUiState>
    val uiState: StateFlow<OrientationUiState> get() = _uiState

    private val _eventState: MutableStateFlow<OrientationEventState> =
        MutableStateFlow(OrientationEventState.Default)

    val eventState: StateFlow<OrientationEventState>
        get() = _eventState

    init {
        OrientationUiState.default.copy(
            permissionState = OrientationUiState.PermissionState.default.copy(
                onClick = ::onResultCheck,
            ),
        ).let { _uiState = MutableStateFlow(it) }
    }

    private fun onResultCheck(permissionResult: Int) = onResultCheck(
        isGranted = permissionResult == PackageManager.PERMISSION_GRANTED,
    )

    private fun onResultCheck(isGranted: Boolean) {
        if (isGranted) onGrant() else onPend()
    }

    fun onResult(isGranted: Boolean) {
        if (isGranted) onGrant() else onDeny()
    }

    private fun onGrant() = navigateFromOrientationToBrowse()

    private fun onPend() = _eventState.update {
        OrientationEventState.Event(::resetPermissionState)
    }

    private fun onDeny() = _uiState.update {
        it.copy(permissionState = OrientationUiState.PermissionState.Deny(::onResultCheck))
    }

    private fun resetPermissionState() = _eventState.update {
        OrientationEventState.Default
    }
}
