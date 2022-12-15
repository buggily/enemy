package com.buggily.enemy.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.buggily.enemy.EnemyState
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
fun EnemyBottomBar(
    controllerState: EnemyState.ControllerState,
    repeatState: EnemyState.RepeatState,
    shuffleState: EnemyState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    ControllerSheet(
        controllerState = controllerState,
        repeatState = repeatState,
        shuffleState = shuffleState,
        modifier = modifier,
    )
}

@Composable
private fun ControllerSheet(
    controllerState: EnemyState.ControllerState,
    repeatState: EnemyState.RepeatState,
    shuffleState: EnemyState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = controllerState.isVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier,
    ) {
        Surface(Modifier.fillMaxSize()) {
            when (val item: MediaItem? = remember(controllerState) { controllerState.item }) {
                is MediaItem -> ControllerBackground(
                    item = item,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(ContentAlpha.low),
                )
            }

            if (controllerState.isExpanded) {
                ControllerSheetForeground(
                    controllerState = controllerState,
                    repeatState = repeatState,
                    shuffleState = shuffleState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(dimens.padding_large_extra))
                        .systemBarsPadding(),
                )
            } else {
                ControllerBottomSheetForeground(
                    controllerState = controllerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(dimens.padding_large))
                        .navigationBarsPadding(),
                )
            }
        }
    }
}

@Composable
private fun ControllerSheetForeground(
    controllerState: EnemyState.ControllerState,
    repeatState: EnemyState.RepeatState,
    shuffleState: EnemyState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    BackHandler { controllerState.onClick() }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        when (val item: MediaItem? = remember(controllerState) { controllerState.item }) {
            is MediaItem -> ControllerSheetText(
                item = item,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.Bottom,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            ControllerPlaybackControls(
                playState = controllerState.playState,
                nextState = controllerState.nextState,
                previousState = controllerState.previousState,
                modifier = Modifier.fillMaxWidth(),
            )

            EnemyControllerPlaylistControls(
                repeatState = repeatState,
                shuffleState = shuffleState,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ControllerSheetText(
    item: MediaItem,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_medium),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        SingleLineText(
            text = item.nameText,
            style = MaterialTheme.typography.titleLarge,
        )

        SingleLineText(
            text = item.artistText,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}

@Composable
private fun ControllerBottomSheetForeground(
    controllerState: EnemyState.ControllerState,
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
        when (val item: MediaItem? = remember { controllerState.item }) {
            is MediaItem -> ControllerBottomSheetText(
                item = item,
                modifier = Modifier.weight(1f),
            )
        }

        ControllerPlaybackControls(
            playState = controllerState.playState,
            nextState = controllerState.nextState,
            previousState = controllerState.previousState,
            modifier = Modifier.width(IntrinsicSize.Min),
        )
    }
}


@Composable
private fun ControllerBottomSheetText(
    item: MediaItem,
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
            text = item.nameText,
            style = MaterialTheme.typography.titleMedium,
        )

        SingleLineText(
            text = item.artistText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}

@Composable
private fun ControllerBackground(
    item: MediaItem,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        item = item,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Composable
private fun ControllerPlaybackControls(
    playState: EnemyState.ControllerState.PlayState,
    nextState: EnemyState.ControllerState.NextState,
    previousState: EnemyState.ControllerState.PreviousState,
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
private fun EnemyControllerPlaylistControls(
    repeatState: EnemyState.RepeatState,
    shuffleState: EnemyState.ShuffleState,
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
    playState: EnemyState.ControllerState.PlayState,
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
    nextState: EnemyState.ControllerState.NextState,
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
    previousState: EnemyState.ControllerState.PreviousState,
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
    repeatState: EnemyState.RepeatState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (repeatState.mode) {
        is EnemyState.RepeatState.Mode.Off -> drawables.repeat_off
        is EnemyState.RepeatState.Mode.On.One -> drawables.repeat_on_one
        is EnemyState.RepeatState.Mode.On.All -> drawables.repeat_on_all
    }

    val stringResId: Int = when (repeatState.mode) {
        is EnemyState.RepeatState.Mode.Off -> strings.repeat_off
        is EnemyState.RepeatState.Mode.On.One -> strings.repeat_on_one
        is EnemyState.RepeatState.Mode.On.All -> strings.repeat_on_all
    }

    val alpha: Float = when (repeatState.mode) {
        is EnemyState.RepeatState.Mode.Off -> ContentAlpha.low
        is EnemyState.RepeatState.Mode.On -> ContentAlpha.max
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
    shuffleState: EnemyState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = when (shuffleState.mode) {
        is EnemyState.ShuffleState.Mode.Off -> drawables.shuffle_off
        is EnemyState.ShuffleState.Mode.On -> drawables.shuffle_on
    }

    val stringResId: Int = when (shuffleState.mode) {
        is EnemyState.ShuffleState.Mode.Off -> strings.shuffle_off
        is EnemyState.ShuffleState.Mode.On -> strings.shuffle_on
    }

    val alpha: Float = when (shuffleState.mode) {
        is EnemyState.ShuffleState.Mode.Off -> ContentAlpha.low
        is EnemyState.ShuffleState.Mode.On -> ContentAlpha.max
    }

    IconButton(
        onClick = shuffleState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier.alpha(alpha),
    )
}
