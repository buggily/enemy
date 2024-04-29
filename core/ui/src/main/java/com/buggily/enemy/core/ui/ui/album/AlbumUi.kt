package com.buggily.enemy.core.ui.ui.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.buggily.enemy.core.data.Artable
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.core.ui.ui.ItemTextMajor
import com.buggily.enemy.core.ui.ui.ItemTextMinor
import com.buggily.enemy.domain.album.AlbumUi
import com.buggily.enemy.domain.track.TrackUi

@Composable
fun AlbumItem(
    album: AlbumUi,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        AlbumItemImage(
            nameText = album.nameText,
            artistNameText = album.artist.nameText,
            artable = album,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun TrackAlbumItem(
    album: TrackUi.Album,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        AlbumItemImage(
            nameText = album.nameText,
            artistNameText = album.artist.nameText,
            artable = album,
            modifier = Modifier.fillMaxSize(),
        )
    }
}


@Composable
private fun AlbumItemImage(
    nameText: String,
    artistNameText: String,
    artable: Artable,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artable = artable,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        error = {
            AlbumItemImageError(
                nameText = nameText,
                artistNameText = artistNameText,
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun AlbumItemImageError(
    nameText: String,
    artistNameText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_small),
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        ItemTextMajor(
            text = nameText,
            textAlign = TextAlign.Center,
        )

        ItemTextMinor(
            text = artistNameText,
            textAlign = TextAlign.Center,
        )
    }
}
