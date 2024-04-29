package com.buggily.enemy.core.ui.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.buggily.enemy.core.data.Artable

@Composable
fun ArtImage(
    artable: Artable,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artUri = artable.artUri,
        contentScale = contentScale,
        contentDescription = artable.contentDescription,
        modifier = modifier,
    )
}

@Composable
fun ArtImage(
    mediaItem: MediaItem,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    val artUri: Uri = remember(mediaItem.mediaMetadata) {
        mediaItem.mediaMetadata.artworkUri
    } ?: return

    val contentDescription: String = remember(mediaItem.mediaMetadata) {
        mediaItem.mediaMetadata.title.toString()
    }

    ArtImage(
        artUri = artUri,
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun ArtImage(
    artUri: Uri,
    contentScale: ContentScale,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = artUri,
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun ArtImage(
    artable: Artable,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    error: @Composable () -> Unit,
) {
    val contentDescription: String = artable.contentDescription
    val imageRequest: ImageRequest = ImageRequest.Builder(LocalContext.current)
        .data(artable.artUri)
        .crossfade(true)
        .build()

    SubcomposeAsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        alignment = Alignment.Center,
        contentScale = contentScale,
        modifier = modifier,
        error = { error() },
    )
}
