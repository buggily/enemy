package com.buggily.enemy.local.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = LocalPlaylist.TABLE_NAME)
data class LocalPlaylist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long,

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
