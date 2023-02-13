package com.buggily.enemy.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.domain.track.GetTracksPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    getTracksPaging: GetTracksPaging,
) : ViewModel() {

    val tracks: Flow<PagingData<Track>> = getTracksPaging(String()).cachedIn(viewModelScope)
}
