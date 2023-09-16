package com.buggily.enemy.feature.orientation

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.domain.navigation.NavigateFromOrientationToBrowse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrientationViewModel @Inject constructor(
    private val navigateFromOrientationToBrowse: NavigateFromOrientationToBrowse,
) : ViewModel() {

    private val _uiState: MutableStateFlow<OrientationUiState>
    val uiState: StateFlow<OrientationUiState> get() = _uiState

    private val _eventState: MutableSharedFlow<OrientationEventState> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    val eventState: SharedFlow<OrientationEventState>
        get() = _eventState

    init {
        OrientationUiState(
            permissionState = OrientationUiState.PermissionState.Default(
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

    private fun onGrant() {
        navigateFromOrientationToBrowse()
    }

    private fun onPend() = viewModelScope.launch {
        _eventState.emit(OrientationEventState.ReadPermission)
    }

    private fun onDeny() = _uiState.update {
        it.copy(permissionState = OrientationUiState.PermissionState.Deny(::onResultCheck))
    }
}
