package com.buggily.enemy.tracks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.runtimeText
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.feature.tracks.R
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
fun TracksScreen(
    viewModel: TracksViewModel,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    val tracks: LazyPagingItems<Track> = viewModel.tracks.collectAsLazyPagingItems()

    TracksScreen(
        tracks = tracks,
        trackState = trackState,
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TracksScreen(
    tracks: LazyPagingItems<Track>,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    val itemModifier: Modifier = Modifier.fillMaxWidth()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        stickyHeader { TracksHeader(itemModifier) }

        items(
            items = tracks,
            key = { it.id },
        ) {
            when (it) {
                is Track -> TrackItem(
                    track = it,
                    modifier = itemModifier
                        .padding(
                            horizontal = dimensionResource(dimens.padding_large),
                            vertical = dimensionResource(dimens.padding_large_extra),
                        )
                        .clickable { trackState.onClick(it) },
                )
                else -> Unit
            }
        }
    }
}

@Composable
private fun TracksHeader(
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier,
    ) {
        TrackItem(
            nameText = stringResource(R.string.name),
            artistText = stringResource(R.string.artist),
            runtimeText = stringResource(R.string.runtime),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun TrackItem(
    track: Track,
    modifier: Modifier = Modifier,
) {
    TrackItem(
        nameText = track.nameText,
        artistText = track.artistText,
        runtimeText = track.runtimeText,
        modifier = modifier,
    )
}

@Composable
private fun TrackItem(
    nameText: String,
    artistText: String,
    runtimeText: String,
    modifier: Modifier = Modifier,
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
            text = nameText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f),
        )

        Text(
            text = artistText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(2f)
                .alpha(ContentAlpha.medium),
        )

        Text(
            text = runtimeText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .alpha(ContentAlpha.medium),
        )
    }
}
