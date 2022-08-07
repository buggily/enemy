package com.buggily.enemy.domain.track

import android.content.ContentUris
import android.net.Uri
import com.buggily.enemy.data.query.track.TrackQuerySource
import com.buggily.enemy.domain.Artable
import com.buggily.enemy.domain.Uriable

interface Trackable : Uriable, Artable {

    override val id: Long
    val name: String

    override val contentUri: Uri
        get() = TrackQuerySource.uri

    override val artUri: Uri
        get() = ContentUris.withAppendedId(artContentUri, id)

    override val contentDescription: String
        get() = name
}
