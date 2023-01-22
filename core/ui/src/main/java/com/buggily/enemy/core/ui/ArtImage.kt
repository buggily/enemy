package com.buggily.enemy.core.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.buggily.enemy.core.model.Artable

@Composable
fun ArtImage(
    artable: Artable,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = artable.artUri,
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
    val name: String = mediaItem.mediaMetadata.title?.toString() ?: return
    val artUri: Uri = mediaItem.mediaMetadata.artworkUri ?: return

    val artable: Artable = object : Artable {
        override val artUri: Uri = artUri
        override val contentDescription: String = name
    }

    ArtImage(
        artable = artable,
        contentScale = contentScale,
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
