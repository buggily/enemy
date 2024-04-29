package com.buggily.enemy.domain.track

import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.buggily.enemy.core.data.Artable
import com.buggily.enemy.core.data.Uriable
import kotlin.time.Duration as KDuration

data class TrackUi(
    val id: Long,
    val name: String,
    val album: Album,
    val artist: Artist,
    val position: Position,
    val duration: Duration,
) : Uriable {

    val nameText: String
        get() = name

    val durationText: String
        get() = duration.text

    val trackText: String
        get() = position.track.toString()

    override val uriId: Long
        get() = id

    override val contentUri: Uri
        get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    fun toMediaItem(): MediaItem {
        val requestMetadata: MediaItem.RequestMetadata = MediaItem.RequestMetadata.Builder()
            .setMediaUri(mediaUri)
            .build()

        val mediaMetadata: MediaMetadata = MediaMetadata.Builder()
            .setTitle(nameText)
            .setTrackNumber(position.track)
            .setDiscNumber(position.disc)
            .setArtist(artist.name)
            .setAlbumTitle(album.name)
            .setArtworkUri(album.artUri)
            .setAlbumArtist(album.artist.name)
            .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
            .build()

        return MediaItem.fromUri(uri)
            .buildUpon()
            .setMediaId(mediaId)
            .setRequestMetadata(requestMetadata)
            .setMediaMetadata(mediaMetadata)
            .build()
    }

    data class Artist(
        val id: Long,
        val name: String,
    ) {

        val nameText: String
            get() = name
    }

    data class Album(
        val id: Long,
        val name: String,
        val artist: Artist,
    ) : Artable {

        val nameText: String
            get() = name

        override val artUriId: Long
            get() = id

        override val contentDescription: String
            get() = name

        data class Artist(
            val id: Long,
            val name: String,
        ) {

            val nameText: String
                get() = name
        }
    }

    data class Position(
        val track: Int,
        val disc: Int,
    )

    data class Duration(
        val duration: KDuration,
        val text: String,
    )
}
