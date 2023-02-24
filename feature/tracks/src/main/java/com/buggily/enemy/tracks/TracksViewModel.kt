package com.buggily.enemy.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.domain.track.GetTracksPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    getTracksPaging: GetTracksPaging,
) : ViewModel() {

    private val _state: MutableStateFlow<TracksState> = MutableStateFlow(TracksState.default)
    private val state: StateFlow<TracksState> get() = _state

    val tracks: Flow<PagingData<Track>>

    init {
        val searchState: Flow<TracksState.SearchState> = state.map {
            it.searchState
        }

        val search: Flow<String> = searchState.map {
            it.value
        }

        tracks = search.flatMapMerge { getTracksPaging(it) }.cachedIn(viewModelScope)
    }

    fun onSearchChange(value: String) = _state.update {
        val searchState: TracksState.SearchState = it.searchState.copy(
            value = value,
        )

        it.copy(searchState = searchState)
    }
}
