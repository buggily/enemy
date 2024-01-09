package com.buggily.enemy.core.local

import com.buggily.enemy.local.playlist.LocalPlaylistDao
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackDao
import com.buggily.enemy.local.track.LocalTrackDao

internal interface EnemyDatabaseable {
    fun getLocalTrackDao(): LocalTrackDao
    fun getLocalPlaylistDao(): LocalPlaylistDao
    fun getLocalPlaylistTrackDao(): LocalPlaylistTrackDao
}
