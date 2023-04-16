package com.buggily.enemy.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.ui.SearchableViewModel
import com.buggily.enemy.core.ui.ext.toMediaItem
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.domain.controller.PlayItem
import com.buggily.enemy.domain.track.GetTrackPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val playItem: PlayItem,
    getTrackPaging: GetTrackPaging,
) : ViewModel(), SearchableViewModel {

    private val _uiState: MutableStateFlow<TracksUiState>
    val uiState: StateFlow<TracksUiState> get() = _uiState

    val tracks: Flow<PagingData<Track>>

    init {
        TracksUiState.default.copy(
            trackState = TracksUiState.TrackState.default.copy(
                onClick = ::onTrackClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val searchState: Flow<TracksUiState.SearchState> = uiState.map {
            it.searchState
        }

        val search: Flow<String> = searchState.map {
            it.value
        }.distinctUntilChanged()

        tracks = search.flatMapLatest { getTrackPaging(it) }.cachedIn(viewModelScope)
    }

    override fun onSearchChange(value: String) = _uiState.update {
        it.copy(searchState = it.searchState.copy(value = value))
    }

    private fun onTrackClick(track: Track) = playItem(track.toMediaItem())
}
