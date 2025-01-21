package com.buggily.enemy.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.buggily.enemy.core.ext.isPositive
import kotlin.time.Duration as KDuration

data class ControllerUiState(
    val mediaState: MediaState,
    val playState: PlayState,
    val nextState: NextState,
    val previousState: PreviousState,
    val repeatState: RepeatState,
    val shuffleState: ShuffleState,
    val seekState: SeekState,
) {

    val isVisible: Boolean
        get() = mediaState.hasMediaItem

    data class MediaState(
        val mediaItem: MediaItem?,
        val onClick: () -> Unit,
    ) {

        val nameText: String?
            get() = mediaMetadata?.title?.toString()

        val artistText: String?
            get() = mediaMetadata?.artist?.toString()

        val mediaId: Long?
            get() = mediaItem?.mediaId?.toLongOrNull()

        val hasMediaItem: Boolean
            get() = !lacksMediaItem

        private val lacksMediaItem: Boolean
            get() = mediaItem == null

        private val mediaMetadata: MediaMetadata?
            get() = mediaItem?.mediaMetadata
    }

    data class PlayState(
        val isEnabled: Boolean,
        val isPlaying: Boolean,
        val onClick: () -> Unit,
    )

    data class NextState(
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    )

    data class PreviousState(
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    )

    data class RepeatState(
        val mode: Mode,
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    ) {

        sealed interface Mode {

            sealed interface On : Mode {
                data object One : On
                data object All : On
            }

            data object Off : Mode
        }
    }

    data class ShuffleState(
        val mode: Mode,
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    ) {

        sealed interface Mode {
            data object On : Mode
            data object Off : Mode
        }
    }

    data class SeekState(
        val current: Duration,
        val duration: Duration,
        val onChange: (Float) -> Unit,
        val onChangeFinish: () -> Unit,
    ) {

        private val first: Float = KDuration.ZERO.inWholeSeconds.toFloat()
        private val last: Float = duration.duration.inWholeSeconds.toFloat()
        val range: ClosedFloatingPointRange<Float> = first..last

        val milliseconds: Long
            get() = current.duration.inWholeMilliseconds

        val value: Float
            get() = current.duration.inWholeSeconds.toFloat()

        val progress: Float
            get() = last.takeIf { it.isPositive }?.let { value / it } ?: first

        data class Duration(
            val duration: KDuration,
            val text: String,
        )
    }
}
