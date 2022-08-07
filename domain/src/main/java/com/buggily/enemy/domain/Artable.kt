package com.buggily.enemy.domain

import android.net.Uri

interface Artable {

    val artUri: Uri
    val contentDescription: String

    val artContentUri: Uri
        get() = Uri.parse(URI)

    private companion object {
        private const val URI = "content://media/external/audio/albumart"
    }
}
