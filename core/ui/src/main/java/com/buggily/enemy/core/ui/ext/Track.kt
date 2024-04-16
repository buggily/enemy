package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.ui.model.TrackUi

val TrackUi.Item.nameText: String
    get() = track.nameText

val TrackUi.Item.trackText: String
    get() = track.trackText

val TrackUi.Item.durationText: String
    get() = track.durationText

val TrackUi.Separator.Disc.discText: String
    get() = disc.toString()
