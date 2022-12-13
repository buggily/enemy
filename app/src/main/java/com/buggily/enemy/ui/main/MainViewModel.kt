package com.buggily.enemy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.navigation.NavDestination
import com.buggily.enemy.core.model.ext.indexOfOrNull
import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.ext.map
import com.buggily.enemy.domain.theme.GetTheme
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.feature.album.AlbumState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getTheme: GetTheme,
    private val getTracksByAlbumId: GetTracksByAlbumId,
) : ViewModel() {

    val theme: StateFlow<Theme> = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Theme.default,
    )

    private var _state: MutableStateFlow<MainState>
    val state: StateFlow<MainState> get() = _state

    init {
        MainState.default.copy(
            navigationState = MainState.NavigationState.default.copy(
                onDestinationChange = ::onDestinationChange,
            ),
            controllerState = MainState.ControllerState.default.copy(
                playState = MainState.ControllerState.PlayState.default.copy(
                    onClick = ::onPlayClick,
                ),
                nextState = MainState.ControllerState.NextState.default.copy(
                    onClick = ::onNextClick,
                ),
                previousState = MainState.ControllerState.PreviousState.default.copy(
                    onClick = ::onPreviousClick,
                ),
            ),
            albumTrackState = AlbumState.TrackState.default.copy(
                onTrackClick = ::onTrackClick,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    fun setIsPlaying(isPlaying: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val playState: MainState.ControllerState.PlayState = controllerState.playState.copy(
            isPlaying = isPlaying,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setIsLoading(isLoading: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val playState: MainState.ControllerState.PlayState = controllerState.playState.copy(
            isEnabled = !isLoading,
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

    }

    fun setShuffleMode(shuffleMode: Boolean) = state.value.let {

    }

    fun setHasNext(hasNext: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val nextState: MainState.ControllerState.NextState = controllerState.nextState.copy(
            isEnabled = hasNext,
        )

        setNextStateOfControllerState(nextState)
    }

    fun setHasPrevious(hasPrevious: Boolean) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState

        val previousState: MainState.ControllerState.PreviousState = controllerState.previousState.copy(
            isEnabled = hasPrevious,
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
        val isPlay: Boolean = playState.isPlaying

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

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val album: Track.Album = track.album
        val tracks: List<Track> = getTracksByAlbumId(album.id)

        val items: List<MediaItem> = tracks.map { it.map() }
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch

        val controllerState = MainState.MediaState.ControllerState.Event.Play.With(
            index = index,
            items = items,
            onPlay = { setControllerStateOfMediaState(MainState.MediaState.ControllerState.Default) },
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onRepeatClick() {}
    private fun onShuffleClick() {}

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

    private fun setControllerState(controllerState: MainState.ControllerState) = _state.update {
        it.copy(controllerState = controllerState)
    }

    private fun setMediaState(mediaState: MainState.MediaState) = _state.update {
        it.copy(mediaState = mediaState)
    }
}
