package com.buggily.enemy.feature.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.model.track.TrackUi
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.SingleLineText
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.discText
import com.buggily.enemy.core.ui.ext.durationText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.trackText
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.string as strings

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun AlbumScreen(
    trackState: AlbumState.TrackState,
    viewModel: AlbumViewModel,
    modifier: Modifier = Modifier,
) {
    val state: AlbumState by viewModel.state.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackUi> = viewModel.tracks.collectAsLazyPagingItems()

    AlbumScreen(
        state = state,
        tracks = tracks,
        trackState = trackState,
        modifier = modifier,
    )
}

@Composable
fun AlbumScreen(
    state: AlbumState,
    tracks: LazyPagingItems<TrackUi>,
    trackState: AlbumState.TrackState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        AlbumHeader(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        )

        AlbumTracksColumn(
            tracks = tracks,
            trackState = trackState,
            modifier = Modifier
                .fillMaxSize()
                .weight(2f),
        )
    }
}

@Composable
private fun AlbumHeader(
    state: AlbumState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        when (val album: Album? = state.album) {
            is Album -> AlbumHeaderBackground(
                album = album,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(ContentAlpha.low),
            )
        }

        AlbumHeaderForeground(
            album = state,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(dimensionResource(dimens.padding_large)),
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
private fun AlbumHeaderForeground(
    album: AlbumState,
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
            state = album,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium),
        )

        AlbumHeaderText(
            state = album,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        )
    }
}

@Composable
private fun AlbumHeaderImage(
    state: AlbumState,
    modifier: Modifier = Modifier,
) {
    when (val album: Album? = state.album) {
        is Album -> Box(modifier) {
            ArtImage(
                artable = album,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun AlbumHeaderText(
    state: AlbumState,
    modifier: Modifier = Modifier,
) {
    when (val album: Album? = state.album) {
        is Album -> Column(
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
                modifier = Modifier.alpha(ContentAlpha.medium),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun AlbumTracksColumn(
    tracks: LazyPagingItems<TrackUi>,
    trackState: AlbumState.TrackState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        modifier = modifier.consumedWindowInsets(WindowInsets.navigationBars),
    ) {
        items(
            items = tracks,
            key = {
                when (it) {
                    is TrackUi.Item -> it.track.id
                    is TrackUi.Separator.Disc -> it.disc
                }
            }
        ) {
            when (it) {
                is TrackUi.Item -> AlbumTrackItem(
                    track = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { trackState.onTrackClick(it.track) }
                        .padding(
                            horizontal = dimensionResource(dimens.padding_large),
                            vertical = dimensionResource(dimens.padding_large_extra),
                        ),
                )
                is TrackUi.Separator.Disc -> AlbumDiscItem(
                    separator = it,
                    modifier = Modifier.fillMaxWidth(),
                )
                else -> Unit
            }
        }
    }
}

@Composable
private fun AlbumTrackItem(
    track: TrackUi.Item,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        Text(
            text = track.trackText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )

        Text(
            text = track.nameText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = track.durationText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}

@Composable
private fun AlbumDiscItem(
    separator: TrackUi.Separator.Disc,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = modifier,
    ) {
        SingleLineText(
            text = stringResource(strings.disc, separator.discText),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}
