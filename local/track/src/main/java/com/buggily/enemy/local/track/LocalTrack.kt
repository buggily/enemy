package com.buggily.enemy.local.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
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

        const val ID = "${TABLE_NAME}_id"
        const val PLAYS = "${TABLE_NAME}_plays"
        const val FIRST_PLAY_INSTANT = "${TABLE_NAME}_first_play_instant"
        const val LAST_PLAY_INSTANT = "${TABLE_NAME}_last_play_instant"
    }
}
