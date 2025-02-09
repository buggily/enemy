package com.buggily.enemy.core.query

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri

abstract class QuerySource<Value>(
    private val contentResolver: ContentResolver,
) {

    protected abstract val uri: Uri
    protected abstract val projection: Array<out String>
    protected abstract fun load(cursor: Cursor): List<Value>

    fun load(query: Query): List<Value> = contentResolver.query(
        uri,
        projection,
        query.bundle,
        null,
    )?.use { load(it) }.orEmpty()

    fun loadFirstOrNull(query: Query): Value? = query.apply {
        limit = Query.Limit(identity = 1)
        offset = Query.Offset(identity = 0)
    }.let { load(it) }.firstOrNull()
}
