package com.buggily.enemy.core.ui.ext

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.buggily.enemy.core.data.Trackable

val Trackable.nameText: String
    get() = name

val Trackable.trackText: String
    get() = position.track.toString()

val Trackable.artistText: String
    get() = artist.name

val Trackable.durationText: String
    get() = duration.text

fun Trackable.toMediaItem(): MediaItem {
    val requestMetadata: MediaItem.RequestMetadata = MediaItem.RequestMetadata.Builder()
        .setMediaUri(mediaUri)
        .build()

    val mediaMetadata: MediaMetadata = MediaMetadata.Builder()
        .setTitle(name)
        .setTrackNumber(position.track)
        .setDiscNumber(position.disc)
        .setArtist(artist.name)
        .setAlbumTitle(album.name)
        .setArtworkUri(album.artUri)
        .setAlbumArtist(album.artist.name)
        .build()

    return MediaItem.fromUri(uri)
        .buildUpon()
        .setMediaId(mediaId)
        .setRequestMetadata(requestMetadata)
        .setMediaMetadata(mediaMetadata)
        .build()
}
