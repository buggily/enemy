package com.buggily.enemy.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.buggily.enemy.local.playlist.Playlist
import com.buggily.enemy.local.playlist.PlaylistDao
import com.buggily.enemy.local.track.Track
import com.buggily.enemy.local.track.TrackDao

@Database(
    version = 1,
    exportSchema = false,

    entities = [
        Playlist::class,
        Track::class,
    ],
)

@TypeConverters(InstantConverter::class)
abstract class EnemyDatabase : RoomDatabase(), EnemyDatabaseable {
    abstract override fun getPlaylistDao(): PlaylistDao
    abstract override fun getTrackDao(): TrackDao
}
