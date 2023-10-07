package com.buggily.enemy.data.playlist

import com.buggily.enemy.core.domain.GetInstantWithMetadataFromInstant
import com.buggily.enemy.local.playlist.LocalPlaylist

fun LocalPlaylist.to(
    getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
): Playlist = Playlist(
    id = id,
    name = name,
    creationInstant = getInstantWithMetadataFromInstant(creationInstant),
    modificationInstant = getInstantWithMetadataFromInstant(modificationInstant),
)

fun Playlist.toLocal(): LocalPlaylist = LocalPlaylist(
    id = id,
    name = name,
    creationInstant = creationInstant.value,
    modificationInstant = modificationInstant.value,
)

