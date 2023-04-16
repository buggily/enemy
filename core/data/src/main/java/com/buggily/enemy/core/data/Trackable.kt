package com.buggily.enemy.core.data

import android.net.Uri
import android.content.ContentUris
import android.provider.MediaStore

interface Trackable : Uriable, Artable {

    override val id: Long
    val name: String

    override val contentUri: Uri
        get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    override val artUri: Uri
        get() = ContentUris.withAppendedId(artContentUri, id)

    override val contentDescription: String
        get() = name
}
