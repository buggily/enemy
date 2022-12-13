package com.buggily.enemy.core.model

import android.content.ContentUris
import android.net.Uri

interface Uriable {

    val id: Long
    val contentUri: Uri

    val mediaUri: Uri
        get() = uri

    val mediaId: String
        get() = id.toString()

    val uri: Uri
        get() = ContentUris.withAppendedId(contentUri, id)
}
