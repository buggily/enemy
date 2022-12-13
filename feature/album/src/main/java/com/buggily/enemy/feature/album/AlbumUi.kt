package com.buggily.enemy.feature.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun AlbumScreen(
    trackState: AlbumState.TrackState,
    viewModel: AlbumViewModel,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val state: AlbumState by viewModel.state.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<Track> = viewModel.tracks.collectAsLazyPagingItems()

    AlbumScreen(
        state = state,
        tracks = tracks,
        trackState = trackState,
        contentPadding = contentPadding,
        modifier = modifier,
    )
}

@Composable
fun AlbumScreen(
    state: AlbumState,
    tracks: LazyPagingItems<Track>,
    trackState: AlbumState.TrackState,
    contentPadding: PaddingValues,
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
            contentPadding = contentPadding,
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
    Box(modifier) {
        AlbumHeaderBackground(
            state = state,
            modifier = Modifier.fillMaxSize(),
        )

        AlbumHeaderForeground(
            album = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(dimens.padding_large))
                .statusBarsPadding(),
        )
    }
}

@Composable
private fun AlbumHeaderBackground(
    state: AlbumState,
    modifier: Modifier = Modifier,
) {
    when (val album: Album? = remember(state) { state.album }) {
        is Album -> Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = modifier,
        ) {
            ArtImage(
                artable = album,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(ContentAlpha.low),
            )
        }
    }
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
    when (val album: Album? = remember(state) { state.album }) {
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
    when (val album: Album? = remember(state) { state.album }) {
        is Album -> Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_small),
                alignment = Alignment.Bottom,
            ),
            horizontalAlignment = Alignment.End,
            modifier = modifier,
        ) {
            Text(
                text = album.name,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = album.artist.name,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.alpha(ContentAlpha.medium),
            )
        }
    }
}

@Composable
private fun AlbumTracksColumn(
    tracks: LazyPagingItems<Track>,
    trackState: AlbumState.TrackState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        items(
            items = tracks,
            key = { it.id },
        ) {
            when (it) {
                is Track -> AlbumTrackItem(
                    track = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { trackState.onTrackClick(it) }
                        .padding(
                            horizontal = dimensionResource(dimens.padding_large),
                            vertical = dimensionResource(dimens.padding_large_extra),
                        ),
                )
            }
        }
    }
}

@Composable
private fun AlbumTrackItem(
    track: Track,
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
            text = track.position.track.toString(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )

        Text(
            text = track.name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = track.duration.text,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}
