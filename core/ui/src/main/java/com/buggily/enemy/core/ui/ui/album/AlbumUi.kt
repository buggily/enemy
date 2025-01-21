package com.buggily.enemy.core.ui.ui.album

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.buggily.enemy.core.data.Artable
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.domain.album.AlbumUi
import com.buggily.enemy.domain.track.TrackUi

@Composable
fun AlbumItem(
    album: AlbumUi,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        AlbumItemImage(
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
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        AlbumItemImage(
            artable = album,
            modifier = Modifier.fillMaxSize(),
        )
    }
}


@Composable
private fun AlbumItemImage(
    artable: Artable,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artable = artable,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}
