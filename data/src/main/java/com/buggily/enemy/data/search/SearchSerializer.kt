package com.buggily.enemy.data.search

import androidx.datastore.core.Serializer
import com.buggily.enemy.data.Search
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object SearchSerializer : Serializer<Search> {

    override val defaultValue: Search = Search.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): Search = try {
        Search.parseFrom(input)
    } catch (e: IOException) {
        defaultValue
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: Search, output: OutputStream) = try {
        t.writeTo(output)
    } catch (e: IOException) {

    }
}
