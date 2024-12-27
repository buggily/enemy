package com.buggily.enemy.domain.resume

import com.buggily.enemy.data.resume.Resume

fun ResumeUi.to(): Resume = Resume(
    id = id,
    position = position,
    source = source.to(),
)

fun ResumeUi.Source.to(): Resume.Source = when (this) {
    is ResumeUi.Source.Track -> Resume.Source.Track
    is ResumeUi.Source.Album -> Resume.Source.Album(index)
    is ResumeUi.Source.Playlist -> Resume.Source.Playlist(index)
    is ResumeUi.Source.Unknown -> Resume.Source.Unknown
}

fun Resume.toUi(): ResumeUi = ResumeUi(
    id = id,
    position = position,
    source = source.toUi(),
)

fun Resume.Source.toUi(): ResumeUi.Source = when (this) {
    is Resume.Source.Track -> ResumeUi.Source.Track
    is Resume.Source.Album -> ResumeUi.Source.Album(index)
    is Resume.Source.Playlist -> ResumeUi.Source.Playlist(index)
    is Resume.Source.Unknown -> ResumeUi.Source.Unknown
}
