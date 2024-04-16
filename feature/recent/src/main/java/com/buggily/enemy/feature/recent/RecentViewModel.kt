package com.buggily.enemy.feature.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithMetadata
import com.buggily.enemy.domain.navigation.NavigateToAlbum
import com.buggily.enemy.domain.track.GetTrackPagingByPopularity
import com.buggily.enemy.domain.track.GetTrackPagingByRecency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    getTrackPagingByRecency: GetTrackPagingByRecency,
    getTrackPagingByPopularity: GetTrackPagingByPopularity,
    private val navigateToAlbum: NavigateToAlbum,
) : ViewModel() {

    val recentTracks: Flow<PagingData<TrackWithMetadata>> =
        getTrackPagingByRecency().cachedIn(viewModelScope)

    val popularTracks: Flow<PagingData<TrackWithMetadata>> =
        getTrackPagingByPopularity().cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<RecentUiState>
    val uiState: StateFlow<RecentUiState> get() = _uiState

    init {
        RecentUiState(
            trackState = RecentUiState.TrackState(
                onClick = ::onTrackClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }
    }

    private fun onTrackClick(
        track: Track,
    ) = navigateToAlbum(
        albumId = track.album.id,
    )
}
