package com.buggily.enemy.local.playlist.track

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(

    tableName = LocalPlaylistTrack.TABLE_NAME,

    primaryKeys = [
        LocalPlaylistTrack.PLAYLIST_ID,
        LocalPlaylistTrack.INDEX,
    ],
)

data class LocalPlaylistTrack(

    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = PLAYLIST_ID)
    val playlistId: Long,

    @ColumnInfo(name = INDEX)
    val index: Int,
) {

    companion object {

        const val TABLE_NAME = "playlist_track"

        const val ID = "${TABLE_NAME}_id"
        const val PLAYLIST_ID = "${TABLE_NAME}_playlist_id"
        const val INDEX = "${TABLE_NAME}_index"
    }
}
