package com.buggily.enemy.ui.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.ZeroCornerSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.R
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.track.Track
import com.buggily.enemy.ext.nullableItems
import com.buggily.enemy.ext.orientationCompat
import com.buggily.enemy.ui.ContentAlpha
import com.buggily.enemy.ui.ext.ArtImage
import com.buggily.enemy.ui.ext.Gradient
import com.buggily.enemy.ui.ext.IconFloatingActionButton
import com.buggily.enemy.ui.main.MainState
import com.buggily.enemy.ui.main.MainViewModel

@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val state: AlbumState by viewModel.state.collectAsStateWithLifecycle()
    val mainState: MainState by mainViewModel.state.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<Result<Track>> = viewModel.tracks.collectAsLazyPagingItems()

    AlbumScreen(
        albumState = state.albumState,
        playState = mainState.playState,
        trackState = mainState.trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
private fun AlbumScreen(
    albumState: AlbumState.State,
    playState: MainState.PlayState,
    trackState: MainState.TrackState,
    tracks: LazyPagingItems<Result<Track>>,
    modifier: Modifier = Modifier,
) {
    val weightMultiplicand: Int = when (LocalConfiguration.current.orientationCompat) {
        Orientation.Horizontal -> 2
        else -> 1
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        AlbumHeader(
            albumState = albumState,
            playState = playState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f * weightMultiplicand),
        )

        AlbumTracksColumn(
            trackState = trackState,
            tracks = tracks,
            modifier = Modifier
                .fillMaxSize()
                .weight(2f),
        )
    }
}

@Composable
private fun AlbumHeader(
    albumState: AlbumState.State,
    playState: MainState.PlayState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        AlbumHeaderBackground(
            albumState = albumState,
            modifier = Modifier.fillMaxSize(),
        )

        AlbumHeaderForeground(
            albumState = albumState,
            playState = playState,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(dimensionResource(R.dimen.padding_large)),
        )
    }
}

@Composable
private fun AlbumHeaderBackground(
    albumState: AlbumState.State,
    modifier: Modifier = Modifier,
) {
    val shape: Shape = MaterialTheme.shapes.large.copy(
        topStart = ZeroCornerSize,
        topEnd = ZeroCornerSize,
    )

    when (val album: Album? = remember(albumState) { albumState.album }) {
        is Album -> Surface(
            shape = shape,
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier,
        ) {
            ArtImage(
                artable = album,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(ContentAlpha.LOW),
            )
        }
    }
}

@Composable
private fun AlbumHeaderForeground(
    albumState: AlbumState.State,
    playState: MainState.PlayState,
    modifier: Modifier = Modifier,
) {
    val orientation: Orientation = LocalConfiguration.current.orientationCompat

    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier,
    ) {
        when (orientation) {
            Orientation.Vertical -> AlbumHeaderImage(
                albumState = albumState,
                playState = playState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            )
            else -> Unit
        }

        AlbumHeaderText(
            albumState = albumState,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        )
    }
}

@Composable
private fun AlbumHeaderImage(
    albumState: AlbumState.State,
    playState: MainState.PlayState,
    modifier: Modifier = Modifier,
) {
    when (val album: Album? = remember(albumState) { albumState.album }) {
        is Album -> Box(modifier) {
            ArtImage(
                artable = album,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.BottomStart),
            )

            AlbumPlayButton(
                album = album,
                playState = playState,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = -dimensionResource(R.dimen.padding_medium),
                        y = -dimensionResource(R.dimen.padding_medium),
                    ),
            )
        }
        else -> Unit
    }
}

@Composable
private fun AlbumHeaderText(
    albumState: AlbumState.State,
    modifier: Modifier = Modifier,
) {
    when (val album: Album? = remember(albumState) { albumState.album }) {
        is Album -> Column(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.padding_small),
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
                modifier = Modifier.alpha(ContentAlpha.MEDIUM),
            )
        }
    }
}

@Composable
private fun AlbumTracksColumn(
    trackState: MainState.TrackState,
    tracks: LazyPagingItems<Result<Track>>,
    modifier: Modifier = Modifier,
) {
    val padding: Dp = dimensionResource(R.dimen.padding_large)
    val navigationBarsPadding: PaddingValues = WindowInsets.navigationBars.asPaddingValues()

    val topPadding: Dp = padding + navigationBarsPadding.calculateTopPadding()
    val bottomPadding: Dp = padding + navigationBarsPadding.calculateBottomPadding()

    Box(modifier) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = topPadding,
                bottom = bottomPadding,
            ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize(),
        ) {
            nullableItems(
                items = tracks,
                key = { it.getOrNull()?.id },
            ) {
                when (val track: Track? = remember(it) { it?.getOrNull() }) {
                    is Track -> AlbumTrackItem(
                        track = track,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { trackState.onTrackClick(track) }
                            .padding(
                                horizontal = dimensionResource(R.dimen.padding_large),
                                vertical = dimensionResource(R.dimen.padding_larger),
                            ),
                    )
                }
            }
        }

        Gradient(
            orientation = Orientation.Vertical,
            colors = listOf(
                MaterialTheme.colorScheme.background,
                Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.padding_larger))
                .align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun AlbumTrackItem(
    track: Track,
    modifier: Modifier,
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
            text = track.position.track.toString(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(ContentAlpha.MEDIUM),
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
            modifier = Modifier.alpha(ContentAlpha.MEDIUM),
        )
    }
}

@Composable
private fun AlbumPlayButton(
    album: Album,
    playState: MainState.PlayState,
    modifier: Modifier = Modifier,
) {
    IconFloatingActionButton(
        painter = painterResource(R.drawable.play),
        contentDescription = stringResource(R.string.play),
        onClick = { playState.onPlayClick(album) },
        modifier = modifier,
    )
}
