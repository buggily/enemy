package com.buggily.enemy.core.ui.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import com.buggily.enemy.core.data.Artable

@Composable
fun ArtImage(
    artable: Artable,
    contentScale: ContentScale,
    alpha: Float = DefaultAlpha,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artUri = artable.artUri,
        contentScale = contentScale,
        contentDescription = artable.contentDescription,
        alpha = alpha,
        modifier = modifier,
    )
}

@Composable
fun ArtImage(
    mediaItem: MediaItem,
    contentScale: ContentScale,
    alpha: Float = DefaultAlpha,
    modifier: Modifier = Modifier,
) {
    val artUri: Uri = remember(mediaItem.mediaMetadata) {
        mediaItem.mediaMetadata.artworkUri
    } ?: return

    val contentDescription: String? = remember(mediaItem.mediaMetadata) {
        mediaItem.mediaMetadata.title?.toString()
    }

    ArtImage(
        artUri = artUri,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alpha = alpha,
        modifier = modifier,
    )
}

@Composable
fun ArtImage(
    artUri: Uri,
    contentScale: ContentScale,
    contentDescription: String?,
    alpha: Float = DefaultAlpha,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = artUri,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alpha = alpha,
        modifier = modifier,
    )
}
