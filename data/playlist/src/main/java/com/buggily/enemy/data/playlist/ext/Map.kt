package com.buggily.enemy.data.playlist.ext

import com.buggily.core.domain.GetUiInstantFromInstant
import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.local.playlist.Playlist as LocalPlaylist

fun LocalPlaylist.map(
    getUiInstantFromInstant: GetUiInstantFromInstant,
): Playlist = Playlist(
    id = id,
    name = name,
    creationInstant = getUiInstantFromInstant(creationInstant),
    modificationInstant = getUiInstantFromInstant(modificationInstant),
)

fun Playlist.map(): LocalPlaylist = LocalPlaylist(
    id = id,
    name = name,
    creationInstant = creationInstant.value,
    modificationInstant = modificationInstant.value,
)

