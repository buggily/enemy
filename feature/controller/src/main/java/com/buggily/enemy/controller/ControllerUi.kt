package com.buggily.enemy.controller

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ui.LocalWindowSizeClass
import com.buggily.enemy.core.ui.composable.ArtImage
import com.buggily.enemy.core.ui.composable.IconButton
import com.buggily.enemy.core.ui.composable.SingleLineText
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.drawable as drawables
import com.buggily.enemy.core.ui.R.string as strings

@Composable
fun ControllerScreen(
    viewModel: ControllerViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: ControllerUiState by viewModel.uiState.collectAsStateWithLifecycle()

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
    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> ControllerScreenCompact(
            uiState = uiState,
            modifier = modifier,
        )
        else -> ControllerScreenMedium(
            uiState = uiState,
            modifier = modifier,
        )
    }
}

@Composable
private fun ControllerScreenCompact(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        when (val mediaItem: MediaItem? = uiState.mediaItem) {
            is MediaItem -> ControllerBackground(
                mediaItem = mediaItem,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(floatResource(dimens.alpha_low)),
            )
        }

        ControllerForegroundCompact(
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun ControllerScreenMedium(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        when (val mediaItem: MediaItem? = uiState.mediaItem) {
            is MediaItem -> ControllerBackground(
                mediaItem = mediaItem,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(floatResource(dimens.alpha_low)),
            )
        }

        ControllerForegroundMedium(
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(dimensionResource(dimens.padding_large)),
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
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth(),
        ) {
            when (val mediaItem: MediaItem? = uiState.mediaItem) {
                is MediaItem -> ControllerBackground(
                    mediaItem = mediaItem,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(floatResource(dimens.alpha_low)),
                )
            }

            ControllerBottomSheetForeground(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
                    .padding(dimensionResource(dimens.padding_large)),
            )
        }
    }
}

@Composable
private fun ControllerForegroundCompact(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.End,
        ),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier,
    ) {
        ControllerText(
            mediaItem = uiState.mediaItem,
            modifier = Modifier.weight(1f),
        )

        ControllerControls(
            uiState = uiState,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ControllerForegroundMedium(
    uiState: ControllerUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
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
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.CenterVertically,
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
            space = dimensionResource(dimens.padding_medium),
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        Text(
            text = mediaItem?.nameText ?: String(),
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = mediaItem?.artistText ?: String(),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.alpha(floatResource(dimens.alpha_medium)),
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
    val painterResId: Int = if (playState.isPlaying) drawables.pause else drawables.play
    val stringResId: Int = if (playState.isPlaying) strings.pause else strings.play

    IconButton(
        onClick = playState.onClick,
        enabled = playState.isEnabled,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_large)),
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
        painter = painterResource(drawables.next),
        contentDescription = stringResource(strings.next),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
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
        painter = painterResource(drawables.previous),
        contentDescription = stringResource(strings.previous),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerRepeatButton(
    repeatState: ControllerUiState.RepeatState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (repeatState.mode) {
        is ControllerUiState.RepeatState.Mode.Off -> drawables.repeat_off
        is ControllerUiState.RepeatState.Mode.On.One -> drawables.repeat_on_one
        is ControllerUiState.RepeatState.Mode.On.All -> drawables.repeat_on_all
    }

    val stringResId: Int = when (repeatState.mode) {
        is ControllerUiState.RepeatState.Mode.Off -> strings.repeat_off
        is ControllerUiState.RepeatState.Mode.On.One -> strings.repeat_on_one
        is ControllerUiState.RepeatState.Mode.On.All -> strings.repeat_on_all
    }

    val floatResId: Int = when (repeatState.mode) {
        is ControllerUiState.RepeatState.Mode.Off -> dimens.alpha_low
        is ControllerUiState.RepeatState.Mode.On -> dimens.alpha_high
    }

    IconButton(
        onClick = repeatState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(floatResource(floatResId)),
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerShuffleButton(
    shuffleState: ControllerUiState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (shuffleState.mode) {
        is ControllerUiState.ShuffleState.Mode.Off -> drawables.shuffle_off
        is ControllerUiState.ShuffleState.Mode.On -> drawables.shuffle_on
    }

    val stringResId: Int = when (shuffleState.mode) {
        is ControllerUiState.ShuffleState.Mode.Off -> strings.shuffle_off
        is ControllerUiState.ShuffleState.Mode.On -> strings.shuffle_on
    }

    val floatResId: Int = when (shuffleState.mode) {
        is ControllerUiState.ShuffleState.Mode.Off -> dimens.alpha_low
        is ControllerUiState.ShuffleState.Mode.On -> dimens.alpha_high
    }

    IconButton(
        onClick = shuffleState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(floatResource(floatResId)),
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerSeekBar(
    seekState: ControllerUiState.SeekState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_small),
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier.fillMaxWidth()

        Slider(
            value = seekState.value,
            valueRange = seekState.range,
            onValueChange = seekState.onChange,
            onValueChangeFinished = seekState.onChangeFinish,
            modifier = contentModifier,
        )

        ControllerSeekBarText(
            seekState = seekState,
            modifier = contentModifier,
        )
    }
}

@Composable
private fun ControllerSeekBarText(
    seekState: ControllerUiState.SeekState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ControllerSeekBarText(seekState.current.text)
        Spacer(Modifier.weight(1f))
        ControllerSeekBarText(seekState.duration.text)
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.End,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
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


@Composable
private fun ControllerBottomSheetText(
    mediaItem: MediaItem,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_small),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        SingleLineText(
            text = mediaItem.nameText ?: String(),
            style = MaterialTheme.typography.titleMedium,
        )

        SingleLineText(
            text = mediaItem.artistText ?: String(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(floatResource(dimens.alpha_medium)),
        )
    }
}
