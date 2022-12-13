package com.buggily.enemy.core.model.track

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.buggily.enemy.core.model.Artable
import com.buggily.enemy.core.model.Uriable

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
