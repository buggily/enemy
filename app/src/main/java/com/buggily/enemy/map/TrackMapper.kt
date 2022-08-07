package com.buggily.enemy.map

import androidx.media3.common.MediaMetadata
import com.buggily.enemy.domain.map.UnidirectionalMapper
import androidx.media3.common.MediaItem as Output
import com.buggily.enemy.domain.track.Track as Input

class TrackMapper : UnidirectionalMapper<Input, Output> {

    override fun mapTo(input: Input): Output = with(input) {
        val requestMetadata: Output.RequestMetadata = Output.RequestMetadata.Builder()
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

        Output.fromUri(uri)
            .buildUpon()
            .setMediaId(mediaId)
            .setRequestMetadata(requestMetadata)
            .setMediaMetadata(mediaMetadata)
            .build()
    }
}
