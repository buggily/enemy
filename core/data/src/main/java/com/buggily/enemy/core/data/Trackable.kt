package com.buggily.enemy.core.data

import android.net.Uri
import android.provider.MediaStore

interface Trackable : Uriable {

    override val contentUri: Uri
        get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
}
