package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.data.track.TrackWithMetadata

val TrackWithMetadata.playsText: String
    get() = plays.toString()