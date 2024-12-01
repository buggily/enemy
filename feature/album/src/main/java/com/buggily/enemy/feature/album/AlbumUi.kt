package com.buggily.enemy.feature.album

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.buggily.enemy.core.data.Artable
import com.buggily.enemy.core.ui.LocalWindowSizeClass
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.core.ui.ui.SingleLineText
import com.buggily.enemy.core.ui.ui.SmallPlayButton
import com.buggily.enemy.core.ui.ui.track.AlbumTrackItem
import com.buggily.enemy.core.ui.R as CR

@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: AlbumUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackAlbumUi> = viewModel.tracks.collectAsLazyPagingItems()

    Box(modifier) {
        AlbumScreen(
            uiState = uiState,
            tracks = tracks,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun AlbumScreen(
    uiState: AlbumUiState,
    tracks: LazyPagingItems<TrackAlbumUi>,
    modifier: Modifier = Modifier,
) {
    AlbumScreen(
        albumState = uiState.albumState,
        trackState = uiState.trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
private fun AlbumScreen(
    albumState: AlbumUiState.AlbumState,
    trackState: AlbumUiState.TrackState,
    tracks: LazyPagingItems<TrackAlbumUi>,
    modifier: Modifier = Modifier,
) {
    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> AlbumScreenCompact(
            albumState = albumState,
            trackState = trackState,
            tracks = tracks,
            modifier = modifier,
        )

        else -> AlbumScreenMedium(
            albumState = albumState,
            trackState = trackState,
            tracks = tracks,
            modifier = modifier,
        )
    }
}

@Composable
private fun AlbumScreenCompact(
    albumState: AlbumUiState.AlbumState,
    trackState: AlbumUiState.TrackState,
    tracks: LazyPagingItems<TrackAlbumUi>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxHeight()

        AlbumHeaderCompact(
            albumState = albumState,
            modifier = itemModifier.weight(1f),
        )

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = WindowInsets.systemBars.asPaddingValues(),
            modifier = itemModifier.weight(2f).consumeWindowInsets(WindowInsets.systemBars),
        ) {
            items(
                count = tracks.itemCount,
                key = tracks.itemKey {
                    when (it) {
                        is TrackAlbumUi.Item -> it.track.id
                        is TrackAlbumUi.Separator.Disc -> it.disc
                    }
                },
            ) {
                when (val track: TrackAlbumUi? = tracks[it]) {
                    is TrackAlbumUi.Item -> AlbumTrackItem(
                        track = track.track,
                        onClick = { trackState.onClick(track.track) },
                        onLongClick = { trackState.onLongClick(track.track) },
                    )

                    is TrackAlbumUi.Separator.Disc -> AlbumDiscItem(
                        trackSeparator = track,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    else -> Unit
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun AlbumScreenMedium(
    albumState: AlbumUiState.AlbumState,
    trackState: AlbumUiState.TrackState,
    tracks: LazyPagingItems<TrackAlbumUi>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        modifier = modifier.consumeWindowInsets(WindowInsets.navigationBars),
    ) {
        val itemModifier: Modifier = Modifier.fillMaxWidth()

        stickyHeader {
            AlbumHeaderMedium(
                albumState = albumState,
                modifier = itemModifier.height(IntrinsicSize.Min),
            )
        }

        items(
            count = tracks.itemCount,
            key = tracks.itemKey {
                when (it) {
                    is TrackAlbumUi.Item -> it.track.id
                    is TrackAlbumUi.Separator.Disc -> it.disc
                }
            },
        ) {
            when (val track: TrackAlbumUi? = tracks[it]) {
                is TrackAlbumUi.Item -> AlbumTrackItem(
                    track = track.track,
                    onClick = { trackState.onClick(track.track) },
                    onLongClick = { trackState.onLongClick(track.track) },
                )

                is TrackAlbumUi.Separator.Disc -> AlbumDiscItem(
                    trackSeparator = track,
                    modifier = Modifier.fillMaxWidth(),
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun AlbumHeaderCompact(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxSize()

        AlbumHeaderBackground(
            albumState = albumState,
            modifier = itemModifier.alpha(floatResource(CR.dimen.alpha_low)),
        )

        AlbumHeaderCompactForeground(
            albumState = albumState,
            modifier = itemModifier
                .systemBarsPadding()
                .padding(dimensionResource(CR.dimen.padding_large)),
        )
    }
}

@Composable
private fun AlbumHeaderMedium(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxSize()

        AlbumHeaderBackground(
            albumState = albumState,
            modifier = itemModifier.alpha(floatResource(CR.dimen.alpha_low)),
        )

        AlbumHeaderMediumForeground(
            albumState = albumState,
            modifier = itemModifier
                .statusBarsPadding()
                .padding(dimensionResource(CR.dimen.padding_large)),
        )
    }
}

@Composable
private fun AlbumHeaderCompactForeground(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        AlbumHeaderText(
            albumState = albumState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun AlbumHeaderMediumForeground(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier,
    ) {
        AlbumHeaderImage(
            albumState = albumState,
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.medium),
        )

        AlbumHeaderText(
            albumState = albumState,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AlbumHeaderBackground(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    when (val artable: Artable? = albumState.album) {
        is Artable -> ArtImage(
            artable = artable,
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )

        else -> Unit
    }
}

@Composable
private fun AlbumHeaderImage(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        when (val artable: Artable? = albumState.album) {
            is Artable -> ArtImage(
                artable = artable,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )

            else -> Unit
        }

        SmallPlayButton(
            onClick = albumState.onStartClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(CR.dimen.padding_small)),
            contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
        )
    }
}

@Composable
private fun AlbumHeaderText(
    albumState: AlbumUiState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_small),
            alignment = Alignment.Bottom,
        ),
        horizontalAlignment = Alignment.End,
        modifier = modifier,
    ) {
        Text(
            text = albumState.album?.nameText.orEmpty(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = albumState.album?.artist?.nameText.orEmpty(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(floatResource(CR.dimen.alpha_medium)),
        )
    }
}

@Composable
private fun AlbumDiscItem(
    trackSeparator: TrackAlbumUi.Separator.Disc,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        SingleLineText(
            text = stringResource(
                CR.string.disc,
                trackSeparator.text,
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CR.dimen.padding_large)),
        )
    }
}
