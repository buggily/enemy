package com.buggily.enemy.feature.album

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import androidx.paging.compose.items
import com.buggily.enemy.core.ui.LocalWindowSizeClass
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.composable.ArtImage
import com.buggily.enemy.core.ui.composable.SingleLineText
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.discText
import com.buggily.enemy.core.ui.ext.durationText
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.trackText
import com.buggily.enemy.core.ui.model.TrackUi
import com.buggily.enemy.data.album.Album
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.string as strings

@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: AlbumUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackUi> = viewModel.tracks.collectAsLazyPagingItems()

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
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier = Modifier,
) {
    AlbumScreen(
        album = uiState.album,
        trackState = uiState.trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
private fun AlbumScreen(
    album: Album?,
    trackState: AlbumUiState.TrackState,
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier = Modifier,
) {
    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> AlbumScreenCompact(
            album = album,
            trackState = trackState,
            tracks = tracks,
            modifier = modifier,
        )
        else -> AlbumScreenMedium(
            album = album,
            trackState = trackState,
            tracks = tracks,
            modifier = modifier,
        )
    }
}

@Composable
private fun AlbumScreenCompact(
    album: Album?,
    trackState: AlbumUiState.TrackState,
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxHeight()

        when (album) {
            is Album -> {
                AlbumHeaderCompact(
                    album = album,
                    modifier = itemModifier.weight(1f),
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = WindowInsets.systemBars.asPaddingValues(),
            modifier = itemModifier.weight(2f),
        ) {
            items(
                items = tracks,
                key = {
                    when (it) {
                        is TrackUi.Item -> it.track.id
                        is TrackUi.Separator.Disc -> it.disc
                    }
                },
            ) {
                when (it) {
                    is TrackUi.Item -> AlbumTrackItem(it) {
                        trackState.onClick(it.track)
                    }
                    is TrackUi.Separator.Disc -> AlbumDiscItem(
                        trackSeparator = it,
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
    album: Album?,
    trackState: AlbumUiState.TrackState,
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxWidth()

        when (album) {
            is Album -> stickyHeader {
                AlbumHeaderMedium(
                    album = album,
                    modifier = itemModifier.height(IntrinsicSize.Min),
                )
            }
        }

        items(
            items = tracks,
            key = {
                when (it) {
                    is TrackUi.Item -> it.track.id
                    is TrackUi.Separator.Disc -> it.disc
                }
            },
        ) {
            when (it) {
                is TrackUi.Item -> AlbumTrackItem(it) {
                    trackState.onClick(it.track)
                }
                is TrackUi.Separator.Disc -> AlbumDiscItem(
                    trackSeparator = it,
                    modifier = Modifier.fillMaxWidth(),
                )
                else -> Unit
            }
        }
    }
}

@Composable
private fun AlbumHeaderCompact(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxSize()

        AlbumHeaderBackground(
            album = album,
            modifier = itemModifier.alpha(floatResource(dimens.alpha_low)),
        )

        AlbumHeaderCompactForeground(
            album = album,
            modifier = itemModifier
                .systemBarsPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun AlbumHeaderMedium(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxSize()

        AlbumHeaderBackground(
            album = album,
            modifier = itemModifier.alpha(floatResource(dimens.alpha_low)),
        )

        AlbumHeaderMediumForeground(
            album = album,
            modifier = itemModifier
                .statusBarsPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun AlbumHeaderCompactForeground(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        AlbumHeaderText(
            album = album,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun AlbumHeaderMediumForeground(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier,
    ) {
        AlbumHeaderImage(
            album = album,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium),
        )

        AlbumHeaderText(
            album = album,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AlbumHeaderBackground(
    album: Album,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artable = album,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Composable
private fun AlbumHeaderImage(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        ArtImage(
            artable = album,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun AlbumHeaderText(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_small),
            alignment = Alignment.Bottom,
        ),
        horizontalAlignment = Alignment.End,
        modifier = modifier,
    ) {
        Text(
            text = album.nameText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = album.artistText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(floatResource(dimens.alpha_medium)),
        )
    }
}

@Composable
private fun AlbumTrackItem(
    trackItem: TrackUi.Item,
    onClick: () -> Unit,
) {
    AlbumTrackItem(
        trackItem = trackItem,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(dimens.padding_large_extra_extra))
            .clickable { onClick() }
            .padding(
                horizontal = dimensionResource(dimens.padding_large),
                vertical = dimensionResource(dimens.padding_large_extra),
            ),
    )
}

@Composable
private fun AlbumTrackItem(
    trackItem: TrackUi.Item,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        Text(
            text = trackItem.trackText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(floatResource(dimens.alpha_medium)),
        )

        Text(
            text = trackItem.nameText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = trackItem.durationText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(floatResource(dimens.alpha_medium)),
        )
    }
}

@Composable
private fun AlbumDiscItem(
    trackSeparator: TrackUi.Separator.Disc,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = modifier,
    ) {
        SingleLineText(
            text = stringResource(
                strings.disc,
                trackSeparator.discText
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}
