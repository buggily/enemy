package com.buggily.enemy.data.track.ext

import com.buggily.core.domain.GetRuntime
import com.buggily.enemy.core.model.track.Track as Output
import com.buggily.enemy.local.track.Track as Input

fun Input.map(getRuntime: GetRuntime): Output = Output(
    id = id,
    name = name,
    runtime = getRuntime(duration),
    position = Output.Position(
        track = position.track,
        disc = position.disc,
    ),
    artist = Output.Artist(
        id = artist.id,
        name = artist.name,
    ),
    album = Output.Album(
        id = album.id,
        name = album.name,
        artist = Output.Album.Artist(
            name = album.artist.name,
        ),
    ),
)
