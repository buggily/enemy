package com.buggily.enemy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.buggily.core.domain.GetRuntime
import com.buggily.core.domain.GetTimeOfDay
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.core.ext.indexOfOrNull
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
import kotlinx.coroutines.flow.debounce
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
    private val getTimeOfDay: GetTimeOfDay,
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
    val search: Flow<String>

    init {
        EnemyState.default.copy(
            greetingState = EnemyState.GreetingState.default.copy(
                timeOfDay = getTimeOfDay(),
                onVisible = ::onGreetingVisible,
            ),
            searchState = EnemyState.SearchState.default.copy(
                onClick = ::onSearchClick,
                onChange = ::onSearchChange,
                onClear = ::onSearchClear,
            ),
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

        val searchState: Flow<EnemyState.SearchState> = state.map {
            it.searchState
        }

        search = searchState.map {
            it.value
        }
            .distinctUntilChanged()
            .debounce(1000)

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

    fun setIsPlaying(isPlaying: Boolean) = _state.update {
        val playState: ControllerState.PlayState = it.controllerState.playState.copy(
            isPlaying = isPlaying,
        )

        it.copy(controllerState = it.controllerState.copy(playState = playState))
    }

    fun setIsLoading(isLoading: Boolean) = _state.update {
        val playState: ControllerState.PlayState = it.controllerState.playState.copy(
            isEnabled = !isLoading,
        )

        it.copy(controllerState = it.controllerState.copy(playState = playState))
    }

    fun setPosition(position: Long) {
        onSeekChange(position.toDuration(DurationUnit.MILLISECONDS))
    }

    fun setMediaItem(mediaItem: MediaItem?) = _state.update {
        val controllerState: ControllerState = it.controllerState.copy(
            mediaItem = mediaItem,
        )

        it.copy(controllerState = controllerState)
    }

    fun setRepeatMode(repeatMode: Int) = _state.update {
        val mode: ControllerState.RepeatState.Mode = when (repeatMode) {
            MediaController.REPEAT_MODE_ONE -> ControllerState.RepeatState.Mode.On.One
            MediaController.REPEAT_MODE_ALL -> ControllerState.RepeatState.Mode.On.All
            else -> ControllerState.RepeatState.Mode.Off
        }

        val repeatState: ControllerState.RepeatState = it.controllerState.repeatState.copy(
            mode = mode,
        )

        it.copy(controllerState = it.controllerState.copy(repeatState = repeatState))
    }

    fun setShuffleMode(shuffleMode: Boolean) = _state.update {
        val mode: ControllerState.ShuffleState.Mode = when (shuffleMode) {
            true -> ControllerState.ShuffleState.Mode.On
            false -> ControllerState.ShuffleState.Mode.Off
        }

        val shuffleState: ControllerState.ShuffleState = it.controllerState.shuffleState.copy(
            mode = mode,
        )

        it.copy(controllerState = it.controllerState.copy(shuffleState = shuffleState))
    }

    fun setHasNext(hasNext: Boolean) = _state.update {
        val nextStates: ControllerState.NextStates = it.controllerState.nextStates.copy(
            isEnabled = hasNext,
        )

        it.copy(controllerState = it.controllerState.copy(nextStates = nextStates))
    }

    fun setHasPrevious(hasPrevious: Boolean) = _state.update {
        val previousStates: ControllerState.PreviousStates = it.controllerState.previousStates.copy(
            isEnabled = hasPrevious,
        )

        it.copy(controllerState = it.controllerState.copy(previousStates = previousStates))
    }

    fun setDuration(milliseconds: Long) {
        val start: Long = Duration.ZERO.inWholeMilliseconds
        val endExclusive: Long = Duration.INFINITE.inWholeMilliseconds
        val range: LongRange = start until endExclusive
        if (milliseconds !in range) return

        _state.update {
            val seekState: ControllerState.SeekState = it.controllerState.seekState.copy(
                runtime = getRuntime(milliseconds),
            )

            it.copy(controllerState = it.controllerState.copy(seekState = seekState))
        }
    }

    private fun onGreetingVisible() = _state.update {
        val greetingState: EnemyState.GreetingState = it.greetingState.copy(
            isVisible = !it.greetingState.isVisible,
        )

        it.copy(greetingState = greetingState)
    }

    private fun onSearchClick() = _state.update {
        val searchState: EnemyState.SearchState = it.searchState.copy(
            isVisible = !it.searchState.isVisible,
        )

        it.copy(searchState = searchState)
    }

    private fun onSearchChange(value: String) = _state.update {
        val searchState: EnemyState.SearchState = it.searchState.copy(
            value = value,
        )

        it.copy(searchState = searchState)
    }

    private fun onSearchClear() = _state.update {
        val searchState: EnemyState.SearchState = it.searchState.copy(
            value = EnemyState.SearchState.default.value,
            isVisible = !it.searchState.isVisible,
        )

        it.copy(searchState = searchState)
    }

    private fun onPlayClick() = _state.update {
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

        it.copy(mediaState = mediaState)
    }

    private fun onFirstNextClick() = _state.update {
        val mediaState = EnemyState.MediaState.Event.Next.First(
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
    }

    private fun onLastNextClick() = _state.update {
        val mediaState = EnemyState.MediaState.Event.Next.Last(
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
    }

    private fun onFirstPreviousClick() = _state.update {
        val mediaState = EnemyState.MediaState.Event.Previous.First(
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
    }

    private fun onLastPreviousClick() = _state.update {
        val mediaState = EnemyState.MediaState.Event.Previous.Last(
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
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

        _state.update { it.copy(mediaState = mediaState) }
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val item: MediaItem = track.map()

        val mediaState = EnemyState.MediaState.Event.Play.With(
            index = 0,
            items = listOf(item),
            onEvent = ::resetMediaState,
        )

        _state.update { it.copy(mediaState = mediaState) }
    }

    private fun onRepeatClick() = _state.update {
        val repeatMode: Int = when (it.controllerState.repeatState.mode) {
            ControllerState.RepeatState.Mode.Off -> MediaController.REPEAT_MODE_ONE
            ControllerState.RepeatState.Mode.On.One -> MediaController.REPEAT_MODE_ALL
            ControllerState.RepeatState.Mode.On.All -> MediaController.REPEAT_MODE_OFF
        }

        val mediaState = EnemyState.MediaState.Event.Repeat(
            repeatMode = repeatMode,
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
    }

    private fun onShuffleClick() = _state.update {
        val shuffleMode = when (it.controllerState.shuffleState.mode) {
            ControllerState.ShuffleState.Mode.Off -> true
            ControllerState.ShuffleState.Mode.On -> false
        }

        val mediaState = EnemyState.MediaState.Event.Shuffle(
            shuffleMode = shuffleMode,
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
    }

    private fun onSeekChange(seconds: Float) {
        onSeekChange(seconds.toLong().toDuration(DurationUnit.SECONDS))
    }

    private fun onSeekChange(duration: Duration) = _state.update {
        val seekState: ControllerState.SeekState = it.controllerState.seekState.copy(
            current = getRuntime(duration),
        )

        it.copy(controllerState = it.controllerState.copy(seekState = seekState))
    }

    private fun onSeekChangeFinish() = _state.update {
        val seekState: ControllerState.SeekState = it.controllerState.seekState

        val mediaState = EnemyState.MediaState.Event.Seek(
            milliseconds = seekState.milliseconds,
            onEvent = ::resetMediaState,
        )

        it.copy(mediaState = mediaState)
    }

    private fun resetMediaState() = _state.update {
        it.copy(mediaState = EnemyState.MediaState.Default)
    }
}
