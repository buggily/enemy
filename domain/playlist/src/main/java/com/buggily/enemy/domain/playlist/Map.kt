package com.buggily.enemy.domain.playlist

import com.buggily.enemy.core.domain.GetInstantText
import com.buggily.enemy.data.playlist.Playlist

fun Playlist.toUi(
    getInstantText: GetInstantText,
): PlaylistUi = PlaylistUi(
    id = id,
    name = name,
    modification = PlaylistUi.Modification(
        instant = modificationInstant,
        text = getInstantText(modificationInstant),
    )
)
