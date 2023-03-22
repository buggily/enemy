package com.buggily.enemy.data.playlist.ext

import com.buggily.core.domain.GetUiInstantFromInstant
import com.buggily.enemy.core.model.playlist.Playlist as Output
import com.buggily.enemy.local.playlist.Playlist as Input

fun Input.map(
    getUiInstantFromInstant: GetUiInstantFromInstant,
): Output = Output(
    id = id,
    name = name,
    creationInstant = getUiInstantFromInstant(creationInstant),
    modificationInstant = getUiInstantFromInstant(modificationInstant),
)

fun Output.map(): Input = Input(
    id = id,
    name = name,
    creationInstant = creationInstant.value,
    modificationInstant = modificationInstant.value,
)

