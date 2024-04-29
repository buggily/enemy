package com.buggily.enemy.data.playlist

import com.buggily.enemy.local.playlist.LocalPlaylist

fun LocalPlaylist.to(): Playlist = Playlist(
    id = id,
    name = name,
    creationInstant = creationInstant,
    modificationInstant = modificationInstant,
)

fun Playlist.toLocal(): LocalPlaylist = LocalPlaylist(
    id = id,
    name = name,
    creationInstant = creationInstant,
    modificationInstant = modificationInstant,
)
