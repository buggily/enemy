package com.buggily.enemy.local.album

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.buggily.enemy.core.query.QuerySource

class AlbumQuerySource(
    contentResolver: ContentResolver,
) : QuerySource<Album>(
    contentResolver = contentResolver,
) {

    override val projection: Array<out String>
        get() = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,

            MediaStore.Audio.Albums.ARTIST_ID,
            MediaStore.Audio.Albums.ARTIST,
        )

    override val uri: Uri
        get() = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

    override fun load(cursor: Cursor): List<Album> {
        val albums: MutableList<Album> = mutableListOf()

        val idIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Albums._ID)
        val nameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)

        val artistIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID)
        val artistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

        while (cursor.moveToNext()) {
            val id: Long = cursor.getLongOrNull(idIndex) ?: continue
            val name: String = cursor.getStringOrNull(nameIndex) ?: continue

            val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
            val artistName: String = cursor.getStringOrNull(artistNameIndex) ?: continue

            val artist = Album.Artist(
                id = artistId,
                name = artistName,
            )

            Album(
                id = id,
                name = name,
                artist = artist,
            ).let { albums.add(it) }
        }

        return albums
    }
}
