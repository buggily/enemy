package com.buggily.enemy.tracks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.runtimeText
import com.buggily.enemy.core.ui.theme.ContentAlpha
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
private fun TracksScreen(
    tracks: LazyPagingItems<Track>,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        items(
            items = tracks,
            key = { it.id },
        ) {
            when (it) {
                is Track -> TrackItem(
                    track = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { trackState.onClick(it) }
                        .padding(
                            horizontal = dimensionResource(dimens.padding_large),
                            vertical = dimensionResource(dimens.padding_large_extra),
                        ),
                )
                else -> Unit
            }
        }
    }
}

@Composable
private fun TrackItem(
    track: Track,
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
            text = track.nameText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = track.artistText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .weight(1f),
        )

        Text(
            text = track.runtimeText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}
