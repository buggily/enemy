package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.model.track.TrackUi

val TrackUi.Item.nameText: String
    get() = track.name

val TrackUi.Item.trackText: String
    get() = track.position.track.toString()

val TrackUi.Item.durationText: String
    get() = track.duration.text

val TrackUi.Separator.Disc.discText: String
    get() = disc.toString()
