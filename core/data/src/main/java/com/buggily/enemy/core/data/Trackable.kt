package com.buggily.enemy.core.data

import android.net.Uri
import android.provider.MediaStore

interface Trackable : Uriable {

    val id: Long
    val name: String

    val artist: Artistable
    val album: Albumable

    val position: Positionable
    val duration: Durationable

    override val contentUri: Uri
        get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
}
