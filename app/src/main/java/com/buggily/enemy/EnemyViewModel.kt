package com.buggily.enemy

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
            controllerState = EnemyState.ControllerState.default.copy(
                playState = EnemyState.ControllerState.PlayState.default.copy(
                    onClick = ::onPlayClick,
                ),
                nextState = EnemyState.ControllerState.NextState.default.copy(
                    onClick = ::onNextClick,
                ),
                previousState = EnemyState.ControllerState.PreviousState.default.copy(
                    onClick = ::onPreviousClick,
                ),
                onClick = ::onControllerClick,
            ),
            repeatState = EnemyState.RepeatState.default.copy(
                onClick = ::onRepeatClick,
            ),
            shuffleState = EnemyState.ShuffleState.default.copy(
                onClick = ::onShuffleClick,
            ),
            albumTrackState = AlbumState.TrackState.default.copy(
                onTrackClick = ::onTrackClick,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    fun setIsPlaying(isPlaying: Boolean) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState

        val playState: EnemyState.ControllerState.PlayState = controllerState.playState.copy(
            isPlaying = isPlaying,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setIsLoading(isLoading: Boolean) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState

        val playState: EnemyState.ControllerState.PlayState = controllerState.playState.copy(
            isEnabled = !isLoading,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setItem(item: MediaItem?) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState.copy(
            item = item,
        )

        setControllerState(controllerState)
    }

    fun setRepeatMode(repeatMode: Int) = state.value.let {
        val mode: EnemyState.RepeatState.Mode = when (repeatMode) {
            MediaController.REPEAT_MODE_ONE -> EnemyState.RepeatState.Mode.On.One
            MediaController.REPEAT_MODE_ALL -> EnemyState.RepeatState.Mode.On.All
            else -> EnemyState.RepeatState.Mode.Off
        }

        val repeatState: EnemyState.RepeatState = it.repeatState.copy(
            mode = mode,
        )

        setRepeatState(repeatState)
    }

    fun setShuffleMode(shuffleMode: Boolean) = state.value.let {
        val mode: EnemyState.ShuffleState.Mode = when (shuffleMode) {
            true -> EnemyState.ShuffleState.Mode.On
            false -> EnemyState.ShuffleState.Mode.Off
        }

        val shuffleState: EnemyState.ShuffleState = it.shuffleState.copy(
            mode = mode,
        )

        setShuffleState(shuffleState)
    }

    fun setHasNext(hasNext: Boolean) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState

        val nextState: EnemyState.ControllerState.NextState = controllerState.nextState.copy(
            isEnabled = hasNext,
        )

        setNextStateOfControllerState(nextState)
    }

    fun setHasPrevious(hasPrevious: Boolean) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState

        val previousState: EnemyState.ControllerState.PreviousState =
            controllerState.previousState.copy(
                isEnabled = hasPrevious,
            )

        setPreviousStateOfControllerState(previousState)
    }

    private fun onPlayClick() = state.value.let {
        val playState: EnemyState.ControllerState.PlayState = it.controllerState.playState
        val isPlay: Boolean = playState.isPlaying

        val controllerState: EnemyState.MediaState.ControllerState.Event = if (isPlay) {
            EnemyState.MediaState.ControllerState.Event.Pause(
                onPause = ::resetControllerStateOfMediaState,
            )
        } else {
            EnemyState.MediaState.ControllerState.Event.Play.Without(
                onPlay = ::resetControllerStateOfMediaState,
            )
        }

        setControllerStateOfMediaState(controllerState)
    }

    private fun onNextClick() {
        val controllerState = EnemyState.MediaState.ControllerState.Event.Next(
            onNext = ::resetControllerStateOfMediaState,
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onPreviousClick() {
        val controllerState = EnemyState.MediaState.ControllerState.Event.Previous(
            onPrevious = ::resetControllerStateOfMediaState,
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val album: Track.Album = track.album
        val tracks: List<Track> = getTracksByAlbumId(album.id)

        val items: List<MediaItem> = tracks.map { it.map() }
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch

        val controllerState = EnemyState.MediaState.ControllerState.Event.Play.With(
            index = index,
            items = items,
            onPlay = { setControllerStateOfMediaState(EnemyState.MediaState.ControllerState.Default) },
        )

        setControllerStateOfMediaState(controllerState)
    }

    private fun onControllerClick() = state.value.let {
        val controllerState: EnemyState.ControllerState = with(it.controllerState) {
            copy(isExpanded = !isExpanded)
        }

        setControllerState(controllerState)
    }

    private fun onRepeatClick() = state.value.let {
        val repeatMode: Int = when (it.repeatState.mode) {
            EnemyState.RepeatState.Mode.Off -> MediaController.REPEAT_MODE_ONE
            EnemyState.RepeatState.Mode.On.One -> MediaController.REPEAT_MODE_ALL
            EnemyState.RepeatState.Mode.On.All -> MediaController.REPEAT_MODE_OFF
        }

        val repeatState = EnemyState.MediaState.RepeatState.Event.Set(
            repeatMode = repeatMode,
            onRepeat = ::resetRepeatStateOfMediaState,
        )

        setRepeatStateOfMediaState(repeatState)
    }

    private fun onShuffleClick() = state.value.let {
        val shuffleMode = when (it.shuffleState.mode) {
            EnemyState.ShuffleState.Mode.Off -> true
            EnemyState.ShuffleState.Mode.On -> false
        }

        val shuffleState = EnemyState.MediaState.ShuffleState.Event.Set(
            shuffleMode = shuffleMode,
            onShuffle = ::resetShuffleStateOfMediaState,
        )

        setShuffleStateOfMediaState(shuffleState)
    }

    private fun setPlayStateOfControllerState(
        playState: EnemyState.ControllerState.PlayState,
    ) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState.copy(
            playState = playState,
        )

        setControllerState(controllerState)
    }

    private fun setNextStateOfControllerState(
        nextState: EnemyState.ControllerState.NextState,
    ) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState.copy(
            nextState = nextState,
        )

        setControllerState(controllerState)
    }

    private fun setPreviousStateOfControllerState(
        previousState: EnemyState.ControllerState.PreviousState,
    ) = state.value.let {
        val controllerState: EnemyState.ControllerState = it.controllerState.copy(
            previousState = previousState,
        )

        setControllerState(controllerState)
    }

    private fun setControllerStateOfMediaState(
        controllerState: EnemyState.MediaState.ControllerState,
    ) = state.value.let {
        val mediaState: EnemyState.MediaState = it.mediaState.copy(
            controllerState = controllerState,
        )

        setMediaState(mediaState)
    }

    private fun resetControllerStateOfMediaState() = setControllerStateOfMediaState(
        controllerState = EnemyState.MediaState.ControllerState.Default,
    )

    private fun resetRepeatStateOfMediaState() = setRepeatStateOfMediaState(
        repeatState = EnemyState.MediaState.RepeatState.Default,
    )

    private fun resetShuffleStateOfMediaState() = setShuffleStateOfMediaState(
        shuffleState = EnemyState.MediaState.ShuffleState.Default,
    )

    private fun setRepeatStateOfMediaState(
        repeatState: EnemyState.MediaState.RepeatState,
    ) = state.value.let {
        val mediaState: EnemyState.MediaState = it.mediaState.copy(
            repeatState = repeatState,
        )

        setMediaState(mediaState)
    }

    private fun setShuffleStateOfMediaState(
        shuffleState: EnemyState.MediaState.ShuffleState,
    ) = state.value.let {
        val mediaState: EnemyState.MediaState = it.mediaState.copy(
            shuffleState = shuffleState,
        )

        setMediaState(mediaState)
    }

    private fun setMediaState(
        mediaState: EnemyState.MediaState,
    ) = _state.update {
        it.copy(mediaState = mediaState)
    }

    private fun setControllerState(
        controllerState: EnemyState.ControllerState,
    ) = _state.update {
        it.copy(controllerState = controllerState)
    }

    private fun setRepeatState(
        repeatState: EnemyState.RepeatState,
    ) = _state.update {
        it.copy(repeatState = repeatState)
    }

    private fun setShuffleState(
        shuffleState: EnemyState.ShuffleState,
    ) = _state.update {
        it.copy(shuffleState = shuffleState)
    }
}
