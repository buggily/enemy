package com.buggily.enemy.controller

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.core.ui.ui.IconButton
import com.buggily.enemy.core.ui.ui.SingleLineText
import com.buggily.enemy.core.ui.R as CR

@Composable
fun ControllerScreen(
    viewModel: ControllerViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: ControllerUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    LaunchedEffect(viewModel.isEmpty) {
        viewModel.isEmpty.flowWithLifecycle(
            lifecycle = lifecycle,
            minActiveState = Lifecycle.State.STARTED,
        ).collect { if (it) viewModel.onEmpty() }
    }

    Box(modifier) {
        ControllerScreen(
            uiState = uiState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun ControllerScreen(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = modifier,
    ) {
        when (val mediaItem: MediaItem? = uiState.mediaItem) {
            is MediaItem -> ControllerBackground(
                mediaItem = mediaItem,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(floatResource(CR.dimen.alpha_low)),
            )

            else -> Unit
        }

        ControllerForeground(
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(dimensionResource(CR.dimen.padding_large)),
        )
    }
}

@Composable
fun ControllerBottomSheet(
    viewModel: ControllerViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: ControllerUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ControllerBottomSheet(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun ControllerBottomSheet(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = uiState.isVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = modifier,
        ) {
            when (val mediaItem: MediaItem? = uiState.mediaItem) {
                is MediaItem -> ControllerBackground(
                    mediaItem = mediaItem,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(floatResource(CR.dimen.alpha_low)),
                )
            }

            ControllerBottomSheetForeground(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding(),
            )
        }
    }
}

@Composable
private fun ControllerForeground(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier.fillMaxWidth()

        ControllerText(
            mediaItem = uiState.mediaItem,
            modifier = contentModifier.weight(1f),
        )

        ControllerControls(
            uiState = uiState,
            modifier = contentModifier,
        )
    }
}

@Composable
private fun ControllerControls(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Bottom,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {

        ControllerSeekBar(
            seekState = uiState.seekState,
            modifier = Modifier.fillMaxWidth(),
        )

        ControllerPlaybackControls(
            playState = uiState.playState,
            nextState = uiState.nextState,
            previousState = uiState.previousState,
            modifier = Modifier.fillMaxWidth(),
        )

        ControllerPlaylistControls(
            repeatState = uiState.repeatState,
            shuffleState = uiState.shuffleState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ControllerText(
    mediaItem: MediaItem?,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_medium),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        Text(
            text = mediaItem?.nameText.orEmpty(),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.basicMarquee(),
        )

        Text(
            text = mediaItem?.artistText.orEmpty(),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .basicMarquee()
                .alpha(floatResource(CR.dimen.alpha_medium)),
        )
    }
}


@Composable
private fun ControllerBackground(
    mediaItem: MediaItem,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        mediaItem = mediaItem,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Composable
private fun ControllerPlaybackControls(
    playState: ControllerUiState.PlayState,
    nextState: ControllerUiState.NextState,
    previousState: ControllerUiState.PreviousState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier.weight(1f)

        ControllerPreviousButton(
            previousState = previousState,
            modifier = contentModifier,
        )

        ControllerPlayButton(
            playState = playState,
            modifier = contentModifier,
        )

        ControllerNextButton(
            nextState = nextState,
            modifier = contentModifier,
        )
    }
}

@Composable
private fun ControllerPlaylistControls(
    repeatState: ControllerUiState.RepeatState,
    shuffleState: ControllerUiState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ControllerRepeatButton(
            repeatState = repeatState,
            modifier = Modifier.weight(1f),
        )

        ControllerShuffleButton(
            shuffleState = shuffleState,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ControllerPlayButton(
    playState: ControllerUiState.PlayState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = if (playState.isPlaying) {
        CR.drawable.pause
    } else {
        CR.drawable.play
    }

    val stringResId: Int = if (playState.isPlaying) {
        CR.string.pause
    } else {
        CR.string.play
    }

    IconButton(
        onClick = playState.onClick,
        enabled = playState.isEnabled,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_large)),
    )
}

@Composable
private fun ControllerNextButton(
    nextState: ControllerUiState.NextState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        enabled = nextState.hasNext,
        onClick = nextState.onClick,
        painter = painterResource(CR.drawable.next),
        contentDescription = stringResource(CR.string.next),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
    )
}

@Composable
private fun ControllerPreviousButton(
    previousState: ControllerUiState.PreviousState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        enabled = previousState.hasPrevious,
        onClick = previousState.onClick,
        painter = painterResource(CR.drawable.previous),
        contentDescription = stringResource(CR.string.previous),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
    )
}

@Composable
private fun ControllerRepeatButton(
    repeatState: ControllerUiState.RepeatState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (repeatState.mode) {
        is ControllerUiState.RepeatState.Mode.Off -> CR.drawable.repeat_off
        is ControllerUiState.RepeatState.Mode.On.One -> CR.drawable.repeat_on_one
        is ControllerUiState.RepeatState.Mode.On.All -> CR.drawable.repeat_on_all
    }

    val stringResId: Int = when (repeatState.mode) {
        is ControllerUiState.RepeatState.Mode.Off -> CR.string.repeat_off
        is ControllerUiState.RepeatState.Mode.On.One -> CR.string.repeat_on_one
        is ControllerUiState.RepeatState.Mode.On.All -> CR.string.repeat_on_all
    }

    val floatResId: Int = when (repeatState.mode) {
        is ControllerUiState.RepeatState.Mode.Off -> CR.dimen.alpha_low
        is ControllerUiState.RepeatState.Mode.On -> CR.dimen.alpha_high
    }

    IconButton(
        onClick = repeatState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(floatResource(floatResId)),
        contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
    )
}

@Composable
private fun ControllerShuffleButton(
    shuffleState: ControllerUiState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (shuffleState.mode) {
        is ControllerUiState.ShuffleState.Mode.Off -> CR.drawable.shuffle_off
        is ControllerUiState.ShuffleState.Mode.On -> CR.drawable.shuffle_on
    }

    val stringResId: Int = when (shuffleState.mode) {
        is ControllerUiState.ShuffleState.Mode.Off -> CR.string.shuffle_off
        is ControllerUiState.ShuffleState.Mode.On -> CR.string.shuffle_on
    }

    val floatResId: Int = when (shuffleState.mode) {
        is ControllerUiState.ShuffleState.Mode.Off -> CR.dimen.alpha_low
        is ControllerUiState.ShuffleState.Mode.On -> CR.dimen.alpha_high
    }

    IconButton(
        onClick = shuffleState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(floatResource(floatResId)),
        contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
    )
}

@Composable
private fun ControllerSeekBar(
    seekState: ControllerUiState.SeekState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_small),
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Slider(
            value = seekState.value,
            valueRange = seekState.range,
            onValueChange = seekState.onChange,
            onValueChangeFinished = seekState.onChangeFinish,
            modifier = Modifier.fillMaxWidth(),
        )

        ControllerSeekBarText(
            seekState = seekState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ControllerProgressBar(
    seekState: ControllerUiState.SeekState,
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        progress = { seekState.progress },
        modifier = modifier,
    )
}

@Composable
private fun ControllerSeekBarText(
    seekState: ControllerUiState.SeekState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ControllerSeekBarText(
            text = seekState.current.text,
            modifier = Modifier,
        )

        Spacer(Modifier.weight(1f))

        ControllerSeekBarText(
            text = seekState.duration.text,
            modifier = Modifier,
        )
    }
}

@Composable
private fun ControllerSeekBarText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}

@Composable
private fun ControllerBottomSheetForeground(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        ControllerProgressBar(
            seekState = uiState.seekState,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(CR.dimen.padding_medium),
                alignment = Alignment.End,
            ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CR.dimen.padding_large)),
        ) {
            when (val mediaItem: MediaItem? = uiState.mediaItem) {
                is MediaItem -> ControllerBottomSheetText(
                    mediaItem = mediaItem,
                    modifier = Modifier.weight(1f),
                )
            }

            ControllerPlaybackControls(
                playState = uiState.playState,
                nextState = uiState.nextState,
                previousState = uiState.previousState,
                modifier = Modifier.width(IntrinsicSize.Min),
            )
        }
    }
}


@Composable
private fun ControllerBottomSheetText(
    mediaItem: MediaItem,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_small),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        SingleLineText(
            text = mediaItem.nameText.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.basicMarquee(),
        )

        SingleLineText(
            text = mediaItem.artistText.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .basicMarquee()
                .alpha(floatResource(CR.dimen.alpha_medium)),
        )
    }
}
