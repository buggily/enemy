package com.buggily.enemy.core.data

import android.content.ContentUris
import android.net.Uri

interface Albumable : Artable {

    val id: Long
    val name: String

    override val artUri: Uri
        get() = ContentUris.withAppendedId(artContentUri, id)

    override val contentDescription: String
        get() = name
}
