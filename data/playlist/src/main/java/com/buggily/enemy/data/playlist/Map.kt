package com.buggily.enemy.data.playlist

import com.buggily.enemy.local.playlist.LocalPlaylist
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalPlaylist.to(): Playlist = Playlist(
    id = id,
    name = name,
    creationInstant = creationInstant,
    modificationInstant = modificationInstant,
)

@OptIn(ExperimentalTime::class)
fun Playlist.toLocal(): LocalPlaylist = LocalPlaylist(
    id = id,
    name = name,
    creationInstant = creationInstant,
    modificationInstant = modificationInstant,
)
