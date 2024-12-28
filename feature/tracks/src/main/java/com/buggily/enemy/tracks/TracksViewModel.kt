package com.buggily.enemy.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.ui.SearchableViewModel
import com.buggily.enemy.domain.controller.PlayItem
import com.buggily.enemy.domain.navigation.NavigateToTrackPicker
import com.buggily.enemy.domain.resume.ResumeUi
import com.buggily.enemy.domain.resume.SetResume
import com.buggily.enemy.domain.track.GetTrackPaging
import com.buggily.enemy.domain.track.TrackUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val playItem: PlayItem,
    private val setResume: SetResume,
    private val navigateToTrackPicker: NavigateToTrackPicker,
    getTrackPaging: GetTrackPaging,
) : ViewModel(), SearchableViewModel {

    private val _uiState: MutableStateFlow<TracksUiState>
    val uiState: StateFlow<TracksUiState> get() = _uiState

    val tracks: Flow<PagingData<TrackUi>>

    init {
        TracksUiState(
            trackState = TracksUiState.TrackState(
                onClick = ::onTrackClick,
                onLongClick = ::onTrackLongClick,
            ),
            searchState = TracksUiState.SearchState(
                value = String(),
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

    private fun onTrackClick(track: TrackUi) = viewModelScope.launch {
        playItemAndSetResume(
            trackId = track.id,
            item = track.toMediaItem(),
        )
    }

    private fun onTrackLongClick(track: TrackUi) = navigateToTrackPicker(
        trackId = track.id,
    )

    private fun playItemAndSetResume(
        trackId: Long,
        item: MediaItem,
    ) = viewModelScope.launch {
        playItem(item)

        setResume(
            id = trackId,
            source = ResumeUi.Source.Track,
        )
    }
}
