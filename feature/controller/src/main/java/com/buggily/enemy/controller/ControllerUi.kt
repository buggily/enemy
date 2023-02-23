package com.buggily.enemy.controller

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ui.composable.ArtImage
import com.buggily.enemy.core.ui.composable.IconButton
import com.buggily.enemy.core.ui.composable.SingleLineText
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.drawable as drawables
import com.buggily.enemy.core.ui.R.string as strings

@Composable
fun ControllerScreen(
    state: ControllerState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        when (val mediaItem: MediaItem? = state.mediaItem) {
            is MediaItem -> ControllerBackground(mediaItem)
        }

        ControllerForeground(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
fun ControllerBottomSheet(
    state: ControllerState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        when (val mediaItem: MediaItem? = state.mediaItem) {
            is MediaItem -> ControllerBackground(mediaItem)
        }

        ControllerBottomSheetForeground(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun ControllerForeground(
    state: ControllerState,
    modifier: Modifier = Modifier,
) {
    val isRepeating: Boolean = remember(state.repeatState) {
        when (state.repeatState.mode) {
            is ControllerState.RepeatState.Mode.On -> true
            is ControllerState.RepeatState.Mode.Off -> false
        }
    }

    val isNotRepeating: Boolean = remember(isRepeating) {
        !isRepeating
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        ControllerText(
            mediaItem = state.mediaItem,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ControllerSeekBar(
                seekState = state.seekState,
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                AnimatedVisibility(
                    visible = isNotRepeating,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally(),
                    modifier = Modifier.weight(1f),
                ) {
                    ControllerPreviousButton(
                        isEnabled = state.previousStates.isEnabled,
                        previousState = state.previousStates.first,
                    )
                }

                ControllerPlaybackControls(
                    playState = state.playState,
                    nextStates = state.nextStates,
                    previousStates = state.previousStates,
                    modifier = Modifier
                        .weight(3f)
                        .animateContentSize(),
                )

                AnimatedVisibility(
                    visible = isNotRepeating,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally(),
                    modifier = Modifier.weight(1f),
                ) {
                    ControllerNextButton(
                        isEnabled = state.nextStates.isEnabled,
                        nextState = state.nextStates.last,
                    )
                }
            }

            ControllerPlaylistControls(
                repeatState = state.repeatState,
                shuffleState = state.shuffleState,
                modifier = Modifier.fillMaxWidth(),
            )
        }
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
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}


@Composable
private fun ControllerBackground(mediaItem: MediaItem) {
    ArtImage(
        mediaItem = mediaItem,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .alpha(ContentAlpha.low),
    )
}

@Composable
private fun ControllerPlaybackControls(
    playState: ControllerState.PlayState,
    nextStates: ControllerState.NextStates,
    previousStates: ControllerState.PreviousStates,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {

        ControllerPreviousButton(
            isEnabled = previousStates.isEnabled,
            previousState = previousStates.last,
            modifier = Modifier.weight(1f),
        )

        ControllerPlayButton(
            playState = playState,
            modifier = Modifier.weight(1f),
        )

        ControllerNextButton(
            isEnabled = nextStates.isEnabled,
            nextState = nextStates.first,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ControllerPlaylistControls(
    repeatState: ControllerState.RepeatState,
    shuffleState: ControllerState.ShuffleState,
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
    playState: ControllerState.PlayState,
    modifier: Modifier = Modifier,
) {
    val isPlaying: Boolean = playState.isPlaying
    val painterResId: Int = if (isPlaying) drawables.pause else drawables.play
    val stringResId: Int = if (isPlaying) strings.pause else strings.play

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
    isEnabled: Boolean,
    nextState: ControllerState.NextState,
    modifier: Modifier = Modifier,
) {
    val stringResId: Int = when (nextState) {
        is ControllerState.NextState.First -> strings.next_first
        is ControllerState.NextState.Last -> strings.next_last
    }

    val painterResId: Int = when (nextState) {
        is ControllerState.NextState.First -> drawables.next_first
        is ControllerState.NextState.Last -> drawables.next_last
    }

    IconButton(
        enabled = isEnabled,
        onClick = nextState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerPreviousButton(
    isEnabled: Boolean,
    previousState: ControllerState.PreviousState,
    modifier: Modifier = Modifier,
) {
    val stringResId: Int = when (previousState) {
        is ControllerState.PreviousState.First -> strings.previous_first
        is ControllerState.PreviousState.Last -> strings.previous_last
    }

    val painterResId: Int = when (previousState) {
        is ControllerState.PreviousState.First -> drawables.previous_first
        is ControllerState.PreviousState.Last -> drawables.previous_last
    }

    IconButton(
        enabled = isEnabled,
        onClick = previousState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerRepeatButton(
    repeatState: ControllerState.RepeatState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (repeatState.mode) {
        is ControllerState.RepeatState.Mode.Off -> drawables.repeat_off
        is ControllerState.RepeatState.Mode.On.One -> drawables.repeat_on_one
        is ControllerState.RepeatState.Mode.On.All -> drawables.repeat_on_all
    }

    val stringResId: Int = when (repeatState.mode) {
        is ControllerState.RepeatState.Mode.Off -> strings.repeat_off
        is ControllerState.RepeatState.Mode.On.One -> strings.repeat_on_one
        is ControllerState.RepeatState.Mode.On.All -> strings.repeat_on_all
    }

    val alpha: Float = when (repeatState.mode) {
        is ControllerState.RepeatState.Mode.Off -> ContentAlpha.low
        is ControllerState.RepeatState.Mode.On -> ContentAlpha.max
    }

    IconButton(
        onClick = repeatState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(alpha),
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerShuffleButton(
    shuffleState: ControllerState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (shuffleState.mode) {
        is ControllerState.ShuffleState.Mode.Off -> drawables.shuffle_off
        is ControllerState.ShuffleState.Mode.On -> drawables.shuffle_on
    }

    val stringResId: Int = when (shuffleState.mode) {
        is ControllerState.ShuffleState.Mode.Off -> strings.shuffle_off
        is ControllerState.ShuffleState.Mode.On -> strings.shuffle_on
    }

    val alpha: Float = when (shuffleState.mode) {
        is ControllerState.ShuffleState.Mode.Off -> ContentAlpha.low
        is ControllerState.ShuffleState.Mode.On -> ContentAlpha.max
    }

    IconButton(
        onClick = shuffleState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(alpha),
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun ControllerSeekBar(
    seekState: ControllerState.SeekState,
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
        Slider(
            value = seekState.value,
            valueRange = seekState.range,
            onValueChange = seekState.onChange,
            onValueChangeFinished = seekState.onChangeFinish,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.CenterHorizontally,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ControllerSeekBarText(
                text = seekState.current.text,
                modifier = Modifier,
            )

            Spacer(Modifier.weight(1f))

            ControllerSeekBarText(
                text = seekState.runtime.text,
                modifier = Modifier,
            )
        }
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
    state: ControllerState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ControllerBottomSheetText(
            mediaItem = state.mediaItem,
            modifier = Modifier.weight(1f),
        )

        ControllerPlaybackControls(
            playState = state.playState,
            nextStates = state.nextStates,
            previousStates = state.previousStates,
            modifier = Modifier.width(IntrinsicSize.Min),
        )
    }
}


@Composable
private fun ControllerBottomSheetText(
    mediaItem: MediaItem?,
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
            text = mediaItem?.nameText ?: String(),
            style = MaterialTheme.typography.titleMedium,
        )

        SingleLineText(
            text = mediaItem?.artistText ?: String(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}
