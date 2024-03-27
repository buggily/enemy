package com.buggily.enemy.local.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = LocalTrack.TABLE_NAME)
data class LocalTrack(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = PLAYS)
    val plays: Int,

    @ColumnInfo(name = FIRST_PLAY_INSTANT)
    val firstPlayInstant: Instant,

    @ColumnInfo(name = LAST_PLAY_INSTANT)
    val lastPlayInstant: Instant,
) {

    companion object {

        const val TABLE_NAME = "track"

        const val ID = "id"
        const val PLAYS = "plays"
        const val FIRST_PLAY_INSTANT = "first_play_instant"
        const val LAST_PLAY_INSTANT = "last_play_instant"
    }
}
