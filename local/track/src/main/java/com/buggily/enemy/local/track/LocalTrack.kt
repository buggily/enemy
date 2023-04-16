package com.buggily.enemy.local.track

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = LocalTrack.TABLE_NAME,

    primaryKeys = [
        LocalTrack.PLAYLIST_ID,
        LocalTrack.INDEX,
    ],
)

data class LocalTrack(

    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = PLAYLIST_ID)
    val playlistId: Long,

    @ColumnInfo(name = INDEX)
    val index: Int,
) {

    companion object {
        const val TABLE_NAME = "track"
        const val ID = "${TABLE_NAME}_id"
        const val PLAYLIST_ID = "${TABLE_NAME}_playlist_id"
        const val INDEX = "${TABLE_NAME}_index"
    }
}
