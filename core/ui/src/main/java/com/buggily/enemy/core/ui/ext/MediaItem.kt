package com.buggily.enemy.core.ui.ext

import androidx.media3.common.MediaItem

val MediaItem.nameText: String
    get() = mediaMetadata.title.toString()

val MediaItem.artistText: String
    get() = mediaMetadata.artist.toString()
