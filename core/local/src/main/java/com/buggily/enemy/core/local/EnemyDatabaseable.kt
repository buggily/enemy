package com.buggily.enemy.core.local

import com.buggily.enemy.local.playlist.PlaylistDao
import com.buggily.enemy.local.track.TrackDao

interface EnemyDatabaseable {

    fun getPlaylistDao(): PlaylistDao
    fun getTrackDao(): TrackDao
}
