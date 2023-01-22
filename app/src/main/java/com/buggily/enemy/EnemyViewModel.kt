package com.buggily.enemy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.core.model.ext.indexOfOrNull
import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.ext.map
import com.buggily.enemy.domain.theme.GetTheme
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.ui.EnemyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnemyViewModel @Inject constructor(
    getTheme: GetTheme,
    private val getTracksByAlbumId: GetTracksByAlbumId,
) : ViewModel() {

    val theme: StateFlow<Theme> = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Theme.default,
    )

    private var _state: MutableStateFlow<EnemyState>
    val state: StateFlow<EnemyState> get() = _state

    init {
        EnemyState.default.copy(
            controllerState = ControllerState.default.copy(
                playState = ControllerState.PlayState.default.copy(
                    onClick = ::onPlayClick,
                ),
                nextState = ControllerState.NextState.default.copy(
                    onClick = ::onNextClick,
                ),
                previousState = ControllerState.PreviousState.default.copy(
                    onClick = ::onPreviousClick,
                ),
                repeatState = ControllerState.RepeatState.default.copy(
                    onClick = ::onRepeatClick,
                ),
                shuffleState = ControllerState.ShuffleState.default.copy(
                    onClick = ::onShuffleClick,
                ),
            ),
            albumTrackState = AlbumState.TrackState.default.copy(
                onTrackClick = ::onTrackClick,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    fun setIsPlaying(isPlaying: Boolean) = state.value.let {
        val controllerState: ControllerState = it.controllerState

        val playState: ControllerState.PlayState = controllerState.playState.copy(
            isPlaying = isPlaying,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setIsLoading(isLoading: Boolean) = state.value.let {
        val controllerState: ControllerState = it.controllerState

        val playState: ControllerState.PlayState = controllerState.playState.copy(
            isEnabled = !isLoading,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setMediaItem(mediaItem: MediaItem?) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            mediaItem = mediaItem,
        )

        setControllerState(controllerState)
    }

    fun setRepeatMode(repeatMode: Int) = state.value.let {
        val mode: ControllerState.RepeatState.Mode = when (repeatMode) {
            MediaController.REPEAT_MODE_ONE -> ControllerState.RepeatState.Mode.On.One
            MediaController.REPEAT_MODE_ALL -> ControllerState.RepeatState.Mode.On.All
            else -> ControllerState.RepeatState.Mode.Off
        }

        val controllerState: ControllerState = it.controllerState
        val repeatState: ControllerState.RepeatState = controllerState.repeatState.copy(
            mode = mode,
        )

        setRepeatStateOfControllerState(repeatState)
    }

    fun setShuffleMode(shuffleMode: Boolean) = state.value.let {
        val mode: ControllerState.ShuffleState.Mode = when (shuffleMode) {
            true -> ControllerState.ShuffleState.Mode.On
            false -> ControllerState.ShuffleState.Mode.Off
        }

        val controllerState: ControllerState = it.controllerState
        val shuffleState: ControllerState.ShuffleState = controllerState.shuffleState.copy(
            mode = mode,
        )

        setShuffleStateOfControllerState(shuffleState)
    }

    fun setHasNext(hasNext: Boolean) = state.value.let {
        val controllerState: ControllerState = it.controllerState

        val nextState: ControllerState.NextState = controllerState.nextState.copy(
            isEnabled = hasNext,
        )

        setNextStateOfControllerState(nextState)
    }

    fun setHasPrevious(hasPrevious: Boolean) = state.value.let {
        val controllerState: ControllerState = it.controllerState

        val previousState: ControllerState.PreviousState = controllerState.previousState.copy(
            isEnabled = hasPrevious,
        )

        setPreviousStateOfControllerState(previousState)
    }

    private fun onPlayClick() = state.value.let {
        val playState: ControllerState.PlayState = it.controllerState.playState
        val isPlay: Boolean = playState.isPlaying

        val mediaState: EnemyState.MediaState = if (isPlay) {
            EnemyState.MediaState.Event.Pause(
                onPause = ::resetMediaState,
            )
        } else {
            EnemyState.MediaState.Event.Play.Without(
                onPlay = ::resetMediaState,
            )
        }

        setMediaState(mediaState)
    }

    private fun onNextClick() {
        val mediaState = EnemyState.MediaState.Event.Next(
            onNext = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onPreviousClick() {
        val mediaState = EnemyState.MediaState.Event.Previous(
            onPrevious = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val album: Track.Album = track.album
        val tracks: List<Track> = getTracksByAlbumId(album.id)

        val items: List<MediaItem> = tracks.map { it.map() }
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch

        val mediaState = EnemyState.MediaState.Event.Play.With(
            index = index,
            items = items,
            onPlay = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onRepeatClick() = state.value.let {
        val repeatMode: Int = when (it.controllerState.repeatState.mode) {
            ControllerState.RepeatState.Mode.Off -> MediaController.REPEAT_MODE_ONE
            ControllerState.RepeatState.Mode.On.One -> MediaController.REPEAT_MODE_ALL
            ControllerState.RepeatState.Mode.On.All -> MediaController.REPEAT_MODE_OFF
        }

        val mediaState = EnemyState.MediaState.Event.Repeat(
            repeatMode = repeatMode,
            onRepeat = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onShuffleClick() = state.value.let {
        val shuffleMode = when (it.controllerState.shuffleState.mode) {
            ControllerState.ShuffleState.Mode.Off -> true
            ControllerState.ShuffleState.Mode.On -> false
        }

        val mediaState = EnemyState.MediaState.Event.Shuffle(
            shuffleMode = shuffleMode,
            onShuffle = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun setPlayStateOfControllerState(
        playState: ControllerState.PlayState,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            playState = playState,
        )

        setControllerState(controllerState)
    }

    private fun setNextStateOfControllerState(
        nextState: ControllerState.NextState,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            nextState = nextState,
        )

        setControllerState(controllerState)
    }

    private fun setPreviousStateOfControllerState(
        previousState: ControllerState.PreviousState,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            previousState = previousState,
        )

        setControllerState(controllerState)
    }

    private fun setRepeatStateOfControllerState(
        repeatState: ControllerState.RepeatState,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            repeatState = repeatState,
        )

        setControllerState(controllerState)
    }

    private fun setShuffleStateOfControllerState(
        shuffleState: ControllerState.ShuffleState,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            shuffleState = shuffleState,
        )

        setControllerState(controllerState)
    }

    private fun setMediaState(
        mediaState: EnemyState.MediaState,
    ) = _state.update {
        it.copy(mediaState = mediaState)
    }

    private fun resetMediaState() = setMediaState(
        mediaState = EnemyState.MediaState.Default,
    )

    private fun setControllerState(
        controllerState: ControllerState,
    ) = _state.update {
        it.copy(controllerState = controllerState)
    }
}
