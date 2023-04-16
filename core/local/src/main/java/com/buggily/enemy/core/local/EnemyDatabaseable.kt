package com.buggily.enemy.core.local

import com.buggily.enemy.local.playlist.LocalPlaylistDao
import com.buggily.enemy.local.track.LocalTrackDao

internal interface EnemyDatabaseable {
    fun getLocalPlaylistDao(): LocalPlaylistDao
    fun getLocalTrackDao(): LocalTrackDao
}
