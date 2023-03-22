package com.buggily.enemy.local.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = Playlist.TABLE_NAME)
data class Playlist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long = 0,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = CREATION_INSTANT)
    val creationInstant: Instant,

    @ColumnInfo(name = MODIFICATION_INSTANT)
    val modificationInstant: Instant,
) {

    companion object {
        const val TABLE_NAME = "playlist"

        const val ID = "${TABLE_NAME}_id"
        const val NAME = "${TABLE_NAME}_name"
        const val CREATION_INSTANT = "${TABLE_NAME}_creation_instant"
        const val MODIFICATION_INSTANT = "${TABLE_NAME}_modification_instant"
    }
}
