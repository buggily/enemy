package com.buggily.enemy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.buggily.core.domain.GetRuntime
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.core.ext.indexOfOrNull
import com.buggily.enemy.core.ext.isNegative
import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.ext.map
import com.buggily.enemy.domain.theme.GetTheme
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.tracks.TracksState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class EnemyViewModel @Inject constructor(
    getTheme: GetTheme,
    private val getTracksByAlbumId: GetTracksByAlbumId,
    private val getRuntime: GetRuntime,
) : ViewModel() {

    val theme: StateFlow<Theme> = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Theme.default,
    )

    private var _state: MutableStateFlow<EnemyState>
    val state: StateFlow<EnemyState> get() = _state

    val isPlaying: Flow<Boolean>

    init {
        EnemyState.default.copy(
            controllerState = ControllerState.default.copy(
                playState = ControllerState.PlayState.default.copy(
                    onClick = ::onPlayClick,
                ),
                nextStates = ControllerState.NextStates.default.copy(
                    first = ControllerState.NextState.First.default.copy(
                        onClick = ::onFirstNextClick,
                    ),
                    last = ControllerState.NextState.Last.default.copy(
                        onClick = ::onLastNextClick,
                    ),
                ),
                previousStates = ControllerState.PreviousStates.default.copy(
                    first = ControllerState.PreviousState.First.default.copy(
                        onClick = ::onFirstPreviousClick,
                    ),
                    last = ControllerState.PreviousState.Last.default.copy(
                        onClick = ::onLastPreviousClick,
                    ),
                ),
                repeatState = ControllerState.RepeatState.default.copy(
                    onClick = ::onRepeatClick,
                ),
                shuffleState = ControllerState.ShuffleState.default.copy(
                    onClick = ::onShuffleClick,
                ),
                seekState = ControllerState.SeekState.default.copy(
                    onChange = ::onSeekChange,
                    onChangeFinish = ::onSeekChangeFinish,
                ),
            ),
            albumTrackState = AlbumState.TrackState.default.copy(
                onClick = ::onAlbumTrackClick,
            ),
            tracksTrackState = TracksState.TrackState.default.copy(
                onClick = ::onTrackClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        val controllerState: Flow<ControllerState> = state.map {
            it.controllerState
        }

        val playState: Flow<ControllerState.PlayState> = controllerState.map {
            it.playState
        }

        isPlaying = playState.map {
            it.isPlaying
        }.distinctUntilChanged()
    }

    fun setIsPlaying(isPlaying: Boolean) = state.value.let {
        val playState: ControllerState.PlayState = it.controllerState.playState.copy(
            isPlaying = isPlaying,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setIsLoading(isLoading: Boolean) = state.value.let {
        val playState: ControllerState.PlayState = it.controllerState.playState.copy(
            isEnabled = !isLoading,
        )

        setPlayStateOfControllerState(playState)
    }

    fun setPosition(position: Long) {
        val duration: Duration = position.toDuration(DurationUnit.MILLISECONDS)
        onSeekChange(duration)
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

        val repeatState: ControllerState.RepeatState = it.controllerState.repeatState.copy(
            mode = mode,
        )

        setRepeatStateOfControllerState(repeatState)
    }

    fun setShuffleMode(shuffleMode: Boolean) = state.value.let {
        val mode: ControllerState.ShuffleState.Mode = when (shuffleMode) {
            true -> ControllerState.ShuffleState.Mode.On
            false -> ControllerState.ShuffleState.Mode.Off
        }

        val shuffleState: ControllerState.ShuffleState = it.controllerState.shuffleState.copy(
            mode = mode,
        )

        setShuffleStateOfControllerState(shuffleState)
    }

    fun setHasNext(hasNext: Boolean) = state.value.let {
        val nextStates: ControllerState.NextStates = it.controllerState.nextStates.copy(
            isEnabled = hasNext,
        )

        setNextStatesOfControllerState(nextStates)
    }

    fun setHasPrevious(hasPrevious: Boolean) = state.value.let {
        val previousStates: ControllerState.PreviousStates = it.controllerState.previousStates.copy(
            isEnabled = hasPrevious,
        )

        setPreviousStatesOfControllerState(previousStates)
    }

    fun setDuration(milliseconds: Long) = state.value.let {
        if (milliseconds.isNegative) return@let

        val seekState: ControllerState.SeekState = it.controllerState.seekState.copy(
            runtime = getRuntime(milliseconds),
        )

        setSeekStateOfControllerState(seekState)
    }

    private fun onPlayClick() = state.value.let {
        val playState: ControllerState.PlayState = it.controllerState.playState
        val isPlay: Boolean = playState.isPlaying

        val mediaState: EnemyState.MediaState = if (isPlay) {
            EnemyState.MediaState.Event.Pause(
                onEvent = ::resetMediaState,
            )
        } else {
            EnemyState.MediaState.Event.Play.Without(
                onEvent = ::resetMediaState,
            )
        }

        setMediaState(mediaState)
    }

    private fun onFirstNextClick() {
        val mediaState = EnemyState.MediaState.Event.Next.First(
            onEvent = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onLastNextClick() {
        val mediaState = EnemyState.MediaState.Event.Next.Last(
            onEvent = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onFirstPreviousClick() {
        val mediaState = EnemyState.MediaState.Event.Previous.First(
            onEvent = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onLastPreviousClick() {
        val mediaState = EnemyState.MediaState.Event.Previous.Last(
            onEvent = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onAlbumTrackClick(track: Track) = viewModelScope.launch {
        val album: Track.Album = track.album
        val tracks: List<Track> = getTracksByAlbumId(album.id)

        val items: List<MediaItem> = tracks.map { it.map() }
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch

        val mediaState = EnemyState.MediaState.Event.Play.With(
            index = index,
            items = items,
            onEvent = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val item: MediaItem = track.map()

        val mediaState = EnemyState.MediaState.Event.Play.With(
            index = 0,
            items = listOf(item),
            onEvent = ::resetMediaState,
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
            onEvent = ::resetMediaState,
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
            onEvent = ::resetMediaState,
        )

        setMediaState(mediaState)
    }

    private fun onSeekChange(seconds: Float) {
        val duration: Duration = seconds.toLong().toDuration(DurationUnit.SECONDS)
        onSeekChange(duration)
    }

    private fun onSeekChange(duration: Duration) = state.value.let {
        val seekState: ControllerState.SeekState = it.controllerState.seekState.copy(
            current = getRuntime(duration),
        )

        setSeekStateOfControllerState(seekState)
    }

    private fun onSeekChangeFinish() = state.value.let {
        val seekState: ControllerState.SeekState = it.controllerState.seekState

        val mediaState = EnemyState.MediaState.Event.Seek(
            milliseconds = seekState.milliseconds,
            onEvent = ::resetMediaState,
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

    private fun setNextStatesOfControllerState(
        nextStates: ControllerState.NextStates,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            nextStates = nextStates,
        )

        setControllerState(controllerState)
    }

    private fun setPreviousStatesOfControllerState(
        previousStates: ControllerState.PreviousStates,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            previousStates = previousStates,
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

    private fun setSeekStateOfControllerState(
        seekState: ControllerState.SeekState,
    ) = state.value.let {
        val controllerState: ControllerState = it.controllerState.copy(
            seekState = seekState
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
