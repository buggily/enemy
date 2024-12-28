package com.buggily.enemy.local.resume

import androidx.datastore.core.Serializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream

object LocalResumeSerializer : Serializer<LocalResume> {

    override val defaultValue: LocalResume
        get() = LocalResume.defaultValue

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(
        input: InputStream,
    ): LocalResume = Json.decodeFromStream(
        deserializer = serializer,
        stream = input,
    )

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(
        t: LocalResume,
        output: OutputStream,
    ) = Json.encodeToStream(
        serializer = serializer,
        value = t,
        stream = output,
    )

    private val serializer: KSerializer<LocalResume>
        get() = LocalResume.serializer()
}
