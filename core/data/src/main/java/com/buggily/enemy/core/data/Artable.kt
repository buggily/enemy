package com.buggily.enemy.core.data

import android.content.ContentUris
import android.net.Uri

interface Artable {

    val artUriId: Long
    val contentDescription: String?

    val artUri: Uri
        get() = ContentUris.withAppendedId(artContentUri, artUriId)

    private val artContentUri: Uri
        get() = Uri.parse(URI)

    private companion object {
        private const val URI = "content://media/external/audio/albumart"
    }
}
