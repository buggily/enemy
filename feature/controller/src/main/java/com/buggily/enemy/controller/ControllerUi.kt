package com.buggily.enemy.controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.IconButton
import com.buggily.enemy.core.ui.SingleLineText
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
                .safeContentPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun ControllerForeground(
    state: ControllerState,
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
        when (val mediaItem: MediaItem? = state.mediaItem) {
            is MediaItem -> ControllerText(
                mediaItem = mediaItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ControllerPlaybackControls(
                playState = state.playState,
                nextState = state.nextState,
                previousState = state.previousState,
                modifier = Modifier.fillMaxWidth(),
            )

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
    mediaItem: MediaItem,
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
            text = mediaItem.nameText,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = mediaItem.artistText,
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
    nextState: ControllerState.NextState,
    previousState: ControllerState.PreviousState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ControllerPreviousButton(
            previousState = previousState,
            modifier = Modifier.weight(1f),
        )

        ControllerPlayButton(
            playState = playState,
            modifier = Modifier.weight(1f),
        )

        ControllerNextButton(
            nextState = nextState,
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
    )
}

@Composable
private fun ControllerNextButton(
    nextState: ControllerState.NextState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = nextState.onClick,
        enabled = nextState.isEnabled,
        painter = painterResource(drawables.next),
        contentDescription = stringResource(strings.next),
        modifier = modifier,
    )
}

@Composable
private fun ControllerPreviousButton(
    previousState: ControllerState.PreviousState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = previousState.onClick,
        enabled = previousState.isEnabled,
        painter = painterResource(drawables.previous),
        contentDescription = stringResource(strings.previous),
        modifier = modifier,
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
        when (val mediaItem: MediaItem? = state.mediaItem) {
            is MediaItem -> ControllerBottomSheetText(
                mediaItem = mediaItem,
                modifier = Modifier.weight(1f),
            )
        }

        ControllerPlaybackControls(
            playState = state.playState,
            nextState = state.nextState,
            previousState = state.previousState,
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
            text = mediaItem.nameText,
            style = MaterialTheme.typography.titleMedium,
        )

        SingleLineText(
            text = mediaItem.artistText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}
