package com.buggily.enemy.core.ui.ui.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.core.ui.ui.ItemCell
import com.buggily.enemy.core.ui.ui.ItemTextMajor
import com.buggily.enemy.core.ui.ui.ItemTextMinor
import com.buggily.enemy.data.album.Album

@Composable
fun AlbumItem(
    album: Album,
    onClick: () -> Unit,
) {
    ItemCell(onClick) {
        AlbumItemImage(
            album = album,
            modifier = Modifier.fillMaxSize(),
        )
    }
}


@Composable
private fun AlbumItemImage(
    album: Album,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artable = album,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        error = {
            AlbumItemImageError(
                album = album,
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun AlbumItemImageError(
    album: Album,
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
            text = album.nameText,
            textAlign = TextAlign.Center,
        )

        ItemTextMinor(
            text = album.artistText,
            textAlign = TextAlign.Center,
        )
    }
}
