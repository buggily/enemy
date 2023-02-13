package com.buggily.enemy.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.domain.album.GetAlbumsPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsPaging: GetAlbumsPaging,
) : ViewModel() {

    val albums: Flow<PagingData<Album>> = getAlbumsPaging(String()).cachedIn(viewModelScope)
}
