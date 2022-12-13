package com.buggily.enemy.core.model.album

import android.content.ContentUris
import android.net.Uri
import com.buggily.enemy.core.model.Artable

interface Albumable : Artable {

    val id: Long
    val name: String

    override val artUri: Uri
        get() = ContentUris.withAppendedId(artContentUri, id)

    override val contentDescription: String
        get() = name
}
