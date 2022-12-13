package com.buggily.enemy.core.model

import android.net.Uri

interface Artable {

    val artUri: Uri
    val contentDescription: String

    val artContentUri: Uri
        get() = Uri.parse(uri)

    private companion object {
        private const val uri = "content://media/external/audio/albumart"
    }
}
