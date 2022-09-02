package com.buggily.enemy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.navigation.NavDestination
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.search.Search
import com.buggily.enemy.domain.theme.Theme
import com.buggily.enemy.domain.track.Track
import com.buggily.enemy.domain.use.search.GetSearch
import com.buggily.enemy.domain.use.search.SetSearch
import com.buggily.enemy.domain.use.theme.GetTheme
import com.buggily.enemy.domain.use.theme.dynamic.GetDynamic
import com.buggily.enemy.domain.use.track.GetTracksByAlbumId
import com.buggily.enemy.ext.firstIndex
import com.buggily.enemy.ext.indexOfOrNull
import com.buggily.enemy.map.TrackMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getTheme: GetTheme,
    getDynamic: GetDynamic,
    getSearch: GetSearch,
    private val getTracksByAlbumId: GetTracksByAlbumId,
    private val setSearch: SetSearch,
    private val mapper: TrackMapper,
) : ViewModel() {

    val theme: StateFlow<Theme> = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Theme.Default,
    )

    val dynamic: StateFlow<Theme.Dynamic> = getDynamic().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Theme.Dynamic.Default,
    )

    private var _state: MutableStateFlow<MainState>
    val state: StateFlow<MainState> get() = _state

    init {
        MainState.default.copy(
            navigationState = MainState.NavigationState.default.copy(
                onDestinationChange = ::onDestinationChange,
            ),
            collapsableState = MainState.CollapsableState.default.copy(
                onCollapsableClick = ::onCollapsableClick,
                searchState = MainState.CollapsableState.SearchState.default.copy(
                    onSearchClick = ::onSearchClick,
                ),
                repeatState = MainState.CollapsableState.RepeatState.default.copy(
                    onRepeatClick = ::onRepeatClick,
                ),
                shuffleState = MainState.CollapsableState.ShuffleState.default.copy(
                    onShuffleClick = ::onShuffleClick,
                ),
            ),
            controllerState = MainState.ControllerState.default.copy(
                playState = MainState.ControllerState.PlayState.default.copy(
                    onPlayClick = ::onPlayClick,
                ),
                nextState = MainState.ControllerState.NextState.default.copy(
                    onNextClick = ::onNextClick,
                ),
                previousState = MainState.ControllerState.PreviousState.default.copy(
                    onPreviousClick = ::onPreviousClick,
                ),
            ),
            playState = MainState.PlayState.default.copy(
                onPlayClick = ::onPlayClick,
            ),
            trackState = MainState.TrackState.default.copy(
                onTrackClick = ::onTrackClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        viewModelScope.launch {
            getSearch().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MainState.CollapsableState.SearchState.default.search,
            ).collectLatest { setSearchOfCollapsableSearchState(it) }
        }
    }

    fun setIsPlaying(isPlaying: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val playState: MainState.ControllerState.PlayState = controllerState.playState.copy(
            isPlay = isPlaying,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setItem(item: MediaItem?) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val itemState: MainState.ControllerState.ItemState = controllerState.itemState.copy(
            item = item,
        )

        setItemStateOfControllerState(itemState)
    }

    fun setRepeatMode(repeatMode: Int) = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState

        val isRepeat: Boolean = when (repeatMode) {
            Player.REPEAT_MODE_ALL -> true
            else -> false
        }

        val repeatState: MainState.CollapsableState.RepeatState = collapsableState.repeatState.copy(
            isRepeat = isRepeat,
        )

        setRepeatStateOfCollapsableState(repeatState)
    }

    fun setShuffleMode(shuffleMode: Boolean) = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState

        val shuffleState: MainState.CollapsableState.ShuffleState = collapsableState.shuffleState.copy(
            isShuffle = shuffleMode,
        )

        setShuffleStateOfCollapsableState(shuffleState)
    }

    fun setHasNext(hasNext: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val nextState: MainState.ControllerState.NextState = controllerState.nextState.copy(
            isNext = hasNext,
        )

        setNextStateOfControllerState(nextState)
    }

    fun setHasPrevious(hasPrevious: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val previousState: MainState.ControllerState.PreviousState = controllerState.previousState.copy(
            isPrevious = hasPrevious,
        )

        setPreviousStateOfControllerState(previousState)
    }

    private fun onDestinationChange(destination: NavDestination?) = state.value.let {
        val navigationState: MainState.NavigationState = it.navigationState.copy(
            destination = destination,
        )

        setNavigationState(navigationState)
    }

    private fun onPlayClick() = state.value.let {
        val playState: MainState.ControllerState.PlayState = it.controllerState.playState
        val isPlay: Boolean = playState.isPlay

        val controllerState: MainState.MediaState.ControllerState.Event = if (isPlay) {
            MainState.MediaState.ControllerState.Event.Pause(
                onPause = ::resetControllerStateOfMediaState,
            )
        } else {
            MainState.MediaState.ControllerState.Event.Play.Without(
                onPlay = ::resetControllerStateOfMediaState,
            )
        }

        setControllerStateOfMediaState(controllerState)
    }

    private fun onNextClick() {
        val controllerState = MainState.MediaState.ControllerState.Event.Next(
            onNext = ::resetControllerStateOfMediaState,
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onPreviousClick() {
        val controllerState = MainState.MediaState.ControllerState.Event.Previous(
            onPrevious = ::resetControllerStateOfMediaState,
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onPlayClick(album: Album) = viewModelScope.launch {
        val tracks: List<Track> = getTracksByAlbumId(album.id)

        val index: Int = tracks.firstIndex
        val items: List<MediaItem> = tracks.map { mapper.mapTo(it) }

        val controllerState = MainState.MediaState.ControllerState.Event.Play.With(
            index = index,
            items = items,
            onPlay = ::resetControllerStateOfMediaState,
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val album: Track.Album = track.album
        val tracks: List<Track> = getTracksByAlbumId(album.id)

        val items: List<MediaItem> = tracks.map { mapper.mapTo(it) }
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch

        val controllerState = MainState.MediaState.ControllerState.Event.Play.With(
            index = index,
            items = items,
            onPlay = { setControllerStateOfMediaState(MainState.MediaState.ControllerState.Default) },
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onCollapsableClick() = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState
        val isCollapsable: Boolean = !collapsableState.isCollapsable

        setIsCollapsableOfCollapsableState(isCollapsable)
    }

    private fun onSearchClick() = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState
        val searchState: MainState.CollapsableState.SearchState = collapsableState.searchState

        val search : Search = searchState.search.run {
            copy(isVisible = !isVisible)
        }

        viewModelScope.launch { setSearch(search) }
    }

    private fun onRepeatClick() = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState
        val collapsableRepeatState: MainState.CollapsableState.RepeatState = collapsableState.repeatState

        val repeatMode: Int = when (collapsableRepeatState.isRepeat) {
            false -> Player.REPEAT_MODE_ALL
            else -> Player.REPEAT_MODE_OFF
        }

        val repeatState = MainState.MediaState.RepeatState.Event.Set(
            repeatMode = repeatMode,
            onRepeat = { setRepeatStateOfMediaState(MainState.MediaState.RepeatState.Default) },
        )

        setRepeatStateOfMediaState(repeatState)
    }

    private fun onShuffleClick() = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState
        val collapsableShuffleState: MainState.CollapsableState.ShuffleState = collapsableState.shuffleState
        val shuffleMode: Boolean = !collapsableShuffleState.isShuffle

        val shuffleState = MainState.MediaState.ShuffleState.Event.Set(
            shuffleMode = shuffleMode,
            onShuffle = { setShuffleStateOfMediaState(MainState.MediaState.ShuffleState.Default) },
        )

        setShuffleStateOfMediaState(shuffleState)
    }

    private fun setSearchOfCollapsableSearchState(search: Search) = state.value.let {
        val searchState: MainState.CollapsableState.SearchState = it.collapsableState.searchState.copy(
            search = search,
        )

        setSearchStateOfCollapsableState(searchState)
    }

    private fun setIsCollapsableOfCollapsableState(isCollapsable: Boolean) = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState.copy(
            isCollapsable = isCollapsable,
        )

        setCollapsableState(collapsableState)
    }

    private fun setSearchStateOfCollapsableState(searchState: MainState.CollapsableState.SearchState) = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState.copy(
            searchState = searchState,
        )

        setCollapsableState(collapsableState)
    }

    private fun setRepeatStateOfCollapsableState(repeatState: MainState.CollapsableState.RepeatState) = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState.copy(
            repeatState = repeatState,
        )

        setCollapsableState(collapsableState)
    }

    private fun setShuffleStateOfCollapsableState(shuffleState: MainState.CollapsableState.ShuffleState) = state.value.let {
        val collapsableState: MainState.CollapsableState = it.collapsableState.copy(
            shuffleState = shuffleState,
        )

        setCollapsableState(collapsableState)
    }

    private fun setPlayStateOfControllerState(playState: MainState.ControllerState.PlayState) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            playState = playState,
        )

        setControllerState(controllerState)
    }

    private fun setNextStateOfControllerState(nextState: MainState.ControllerState.NextState) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            nextState = nextState,
        )

        setControllerState(controllerState)
    }

    private fun setPreviousStateOfControllerState(previousState: MainState.ControllerState.PreviousState) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            previousState = previousState,
        )

        setControllerState(controllerState)
    }

    private fun setItemStateOfControllerState(itemState: MainState.ControllerState.ItemState) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            itemState = itemState,
        )

        setControllerState(controllerState)
    }

    private fun setControllerStateOfMediaState(controllerState: MainState.MediaState.ControllerState) = state.value.let {
        val mediaState: MainState.MediaState = it.mediaState.copy(
            controllerState = controllerState,
        )

        setMediaState(mediaState)
    }

    private fun resetControllerStateOfMediaState() = setControllerStateOfMediaState(
        controllerState = MainState.MediaState.ControllerState.Default,
    )

    private fun setRepeatStateOfMediaState(repeatState: MainState.MediaState.RepeatState) = state.value.let {
        val mediaState: MainState.MediaState = it.mediaState.copy(
            repeatState = repeatState,
        )

        setMediaState(mediaState)
    }

    private fun setShuffleStateOfMediaState(shuffleState: MainState.MediaState.ShuffleState) = state.value.let {
        val mediaState: MainState.MediaState = it.mediaState.copy(
            shuffleState = shuffleState,
        )

        setMediaState(mediaState)
    }

    private fun setNavigationState(navigationState: MainState.NavigationState) = _state.update {
        it.copy(navigationState = navigationState)
    }

    private fun setCollapsableState(collapsableState: MainState.CollapsableState) = _state.update {
        it.copy(collapsableState = collapsableState)
    }

    private fun setControllerState(controllerState: MainState.ControllerState) = _state.update {
        it.copy(controllerState = controllerState)
    }

    private fun setMediaState(mediaState: MainState.MediaState) = _state.update {
        it.copy(mediaState = mediaState)
    }
}
