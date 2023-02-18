package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.model.track.TrackUi

val TrackUi.Item.nameText: String
    get() = track.nameText

val TrackUi.Item.trackText: String
    get() = track.trackText

val TrackUi.Item.runtimeText: String
    get() = track.runtimeText

val TrackUi.Separator.Disc.discText: String
    get() = disc.toString()
