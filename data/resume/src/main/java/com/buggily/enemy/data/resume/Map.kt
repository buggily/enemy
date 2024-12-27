package com.buggily.enemy.data.resume

import com.buggily.enemy.local.resume.LocalResume

fun LocalResume.to(): Resume = Resume(
    id = id,
    position = position,
    source = source.to(index),
)

fun LocalResume.Source.to(index: Int): Resume.Source = when (this) {
    LocalResume.Source.Track -> Resume.Source.Track
    LocalResume.Source.Album -> Resume.Source.Album(index)
    LocalResume.Source.Playlist -> Resume.Source.Playlist(index)
    LocalResume.Source.Unknown -> Resume.Source.Unknown
}

fun Resume.toLocal(): LocalResume = LocalResume(
    id = id,
    index = source.index,
    position = position,
    source = source.toLocal(),
)

fun Resume.Source.toLocal(): LocalResume.Source = when (this) {
    is Resume.Source.Track -> LocalResume.Source.Track
    is Resume.Source.Album -> LocalResume.Source.Album
    is Resume.Source.Playlist -> LocalResume.Source.Playlist
    is Resume.Source.Unknown -> LocalResume.Source.Unknown
}