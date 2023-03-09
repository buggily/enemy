package com.buggily.enemy.data.track.ext.paging

import com.buggily.core.domain.GetUiDuration
import com.buggily.enemy.core.model.track.Track as Output
import com.buggily.enemy.local.track.paging.Track as Input

fun Input.map(getUiDuration: GetUiDuration): Output = Output(
    id = id,
    name = name,
    duration = getUiDuration(duration),
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
