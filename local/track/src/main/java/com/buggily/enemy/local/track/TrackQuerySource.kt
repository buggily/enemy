package com.buggily.enemy.local.track

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.buggily.enemy.core.query.QuerySource

class TrackQuerySource(
    contentResolver: ContentResolver,
) : QuerySource<Track>(
    contentResolver = contentResolver,
) {

    override val projection: Array<out String>
        get() = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,

            MediaStore.Audio.Media.CD_TRACK_NUMBER,
            MediaStore.Audio.Media.DISC_NUMBER,

            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.RELATIVE_PATH,

            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,

            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ARTIST,
        )

    override val uri: Uri
        get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    override fun load(cursor: Cursor): List<Track> {
        val tracks: MutableList<Track> = mutableListOf()

        val idIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val nameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)

        val durationIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

        val trackPositionIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.CD_TRACK_NUMBER)
        val discPositionIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DISC_NUMBER)

        val artistIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)
        val artistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)

        val albumIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val albumNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        val albumArtistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST)

        while (cursor.moveToNext()) {
            val id: Long = cursor.getLongOrNull(idIndex) ?: continue
            val name: String = cursor.getStringOrNull(nameIndex) ?: continue

            val trackPosition: Int = cursor.getIntOrNull(trackPositionIndex) ?: continue
            val discPosition: Int = cursor.getIntOrNull(discPositionIndex) ?: Track.Position.defaultDisc

            val duration: Long = cursor.getLongOrNull(durationIndex) ?: continue

            val position = Track.Position(
                track = trackPosition,
                disc = discPosition,
            )

            val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
            val artistName: String = cursor.getStringOrNull(artistNameIndex) ?: continue

            val artist = Track.Artist(
                id = artistId,
                name = artistName,
            )

            val albumId: Long = cursor.getLongOrNull(albumIdIndex) ?: continue
            val albumName: String = cursor.getStringOrNull(albumNameIndex) ?: continue
            val albumArtistName: String = cursor.getStringOrNull(albumArtistNameIndex) ?: continue

            val albumArtist = Track.Album.Artist(
                name = albumArtistName,
            )

            val album = Track.Album(
                id = albumId,
                name = albumName,
                artist = albumArtist,
            )

            Track(
                id = id,
                name = name,
                duration = duration,
                position = position,
                artist = artist,
                album = album,
            ).let { tracks.add(it) }
        }

        return tracks
    }
}
