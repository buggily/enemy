package com.buggily.enemy.core.data

import android.content.ContentUris
import android.net.Uri

interface Albumable : Artable {

    val artUriId: Long

    override val artUri: Uri
        get() = ContentUris.withAppendedId(artContentUri, artUriId)
}
