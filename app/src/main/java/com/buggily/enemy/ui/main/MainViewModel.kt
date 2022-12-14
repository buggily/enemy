package com.buggily.enemy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
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
                onClick = ::onControllerClick,
            ),
            repeatState = MainState.RepeatState.default.copy(
                onClick = ::onRepeatClick,
            ),
            shuffleState = MainState.ShuffleState.default.copy(
                onClick = ::onShuffleClick,
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
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            item = item,
        )

        setControllerState(controllerState)
    }

    fun setRepeatMode(repeatMode: Int) = state.value.let {
        val mode: MainState.RepeatState.Mode = when (repeatMode) {
            MediaController.REPEAT_MODE_ONE -> MainState.RepeatState.Mode.On.One
            MediaController.REPEAT_MODE_ALL -> MainState.RepeatState.Mode.On.All
            else -> MainState.RepeatState.Mode.Off
        }

        val repeatState: MainState.RepeatState = it.repeatState.copy(
            mode = mode,
        )

        setRepeatState(repeatState)
    }

    fun setShuffleMode(shuffleMode: Boolean) = state.value.let {
        val mode: MainState.ShuffleState.Mode = when (shuffleMode) {
            true -> MainState.ShuffleState.Mode.On
            false -> MainState.ShuffleState.Mode.Off
        }

        val shuffleState: MainState.ShuffleState = it.shuffleState.copy(
            mode = mode,
        )

        setShuffleState(shuffleState)
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

        val previousState: MainState.ControllerState.PreviousState =
            controllerState.previousState.copy(
                isEnabled = hasPrevious,
            )

        setPreviousStateOfControllerState(previousState)
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

    private fun onControllerClick() = state.value.let {
        val controllerState: MainState.ControllerState = with(it.controllerState) {
            copy(isExpanded = !isExpanded)
        }

        setControllerState(controllerState)
    }

    private fun onRepeatClick() = state.value.let {
        val repeatMode: Int = when (it.repeatState.mode) {
            MainState.RepeatState.Mode.Off -> MediaController.REPEAT_MODE_ONE
            MainState.RepeatState.Mode.On.One -> MediaController.REPEAT_MODE_ALL
            MainState.RepeatState.Mode.On.All -> MediaController.REPEAT_MODE_OFF
        }

        val repeatState = MainState.MediaState.RepeatState.Event.Set(
            repeatMode = repeatMode,
            onRepeat = ::resetRepeatStateOfMediaState,
        )

        setRepeatStateOfMediaState(repeatState)
    }

    private fun onShuffleClick() = state.value.let {
        val shuffleMode = when (it.shuffleState.mode) {
            MainState.ShuffleState.Mode.Off -> true
            MainState.ShuffleState.Mode.On -> false
        }

        val shuffleState = MainState.MediaState.ShuffleState.Event.Set(
            shuffleMode = shuffleMode,
            onShuffle = ::resetShuffleStateOfMediaState,
        )

        setShuffleStateOfMediaState(shuffleState)
    }

    private fun setPlayStateOfControllerState(
        playState: MainState.ControllerState.PlayState,
    ) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            playState = playState,
        )

        setControllerState(controllerState)
    }

    private fun setNextStateOfControllerState(
        nextState: MainState.ControllerState.NextState,
    ) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            nextState = nextState,
        )

        setControllerState(controllerState)
    }

    private fun setPreviousStateOfControllerState(
        previousState: MainState.ControllerState.PreviousState,
    ) = state.value.let {
        val controllerState: MainState.ControllerState = it.controllerState.copy(
            previousState = previousState,
        )

        setControllerState(controllerState)
    }

    private fun setControllerStateOfMediaState(
        controllerState: MainState.MediaState.ControllerState,
    ) = state.value.let {
        val mediaState: MainState.MediaState = it.mediaState.copy(
            controllerState = controllerState,
        )

        setMediaState(mediaState)
    }

    private fun resetControllerStateOfMediaState() = setControllerStateOfMediaState(
        controllerState = MainState.MediaState.ControllerState.Default,
    )

    private fun resetRepeatStateOfMediaState() = setRepeatStateOfMediaState(
        repeatState = MainState.MediaState.RepeatState.Default,
    )

    private fun resetShuffleStateOfMediaState() = setShuffleStateOfMediaState(
        shuffleState = MainState.MediaState.ShuffleState.Default,
    )

    private fun setRepeatStateOfMediaState(
        repeatState: MainState.MediaState.RepeatState,
    ) = state.value.let {
        val mediaState: MainState.MediaState = it.mediaState.copy(
            repeatState = repeatState,
        )

        setMediaState(mediaState)
    }

    private fun setShuffleStateOfMediaState(
        shuffleState: MainState.MediaState.ShuffleState,
    ) = state.value.let {
        val mediaState: MainState.MediaState = it.mediaState.copy(
            shuffleState = shuffleState,
        )

        setMediaState(mediaState)
    }

    private fun setMediaState(
        mediaState: MainState.MediaState,
    ) = _state.update {
        it.copy(mediaState = mediaState)
    }

    private fun setControllerState(
        controllerState: MainState.ControllerState,
    ) = _state.update {
        it.copy(controllerState = controllerState)
    }

    private fun setRepeatState(
        repeatState: MainState.RepeatState,
    ) = _state.update {
        it.copy(repeatState = repeatState)
    }

    private fun setShuffleState(
        shuffleState: MainState.ShuffleState,
    ) = _state.update {
        it.copy(shuffleState = shuffleState)
    }
}
