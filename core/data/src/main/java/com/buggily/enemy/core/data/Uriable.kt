package com.buggily.enemy.core.data

import android.content.ContentUris
import android.net.Uri

interface Uriable {

    val uriId: Long
    val contentUri: Uri

    val mediaUri: Uri
        get() = uri

    val mediaId: String
        get() = uriId.toString()

    val uri: Uri
        get() = ContentUris.withAppendedId(
            contentUri,
            uriId
        )
}
