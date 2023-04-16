package com.buggily.enemy.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.buggily.enemy.local.playlist.LocalPlaylist
import com.buggily.enemy.local.playlist.LocalPlaylistDao
import com.buggily.enemy.local.track.LocalTrack
import com.buggily.enemy.local.track.LocalTrackDao

@Database(
    version = 1,
    exportSchema = false,

    entities = [
        LocalPlaylist::class,
        LocalTrack::class,
    ],
)

@TypeConverters(InstantConverter::class)
internal abstract class EnemyDatabase : RoomDatabase(), EnemyDatabaseable {
    abstract override fun getLocalPlaylistDao(): LocalPlaylistDao
    abstract override fun getLocalTrackDao(): LocalTrackDao
}
