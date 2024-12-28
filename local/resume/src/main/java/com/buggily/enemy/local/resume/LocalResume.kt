package com.buggily.enemy.local.resume

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocalResume(
    @SerialName(ID) val id: Long,
    @SerialName(INDEX) val index: Int,
    @SerialName(POSITION) val position: Long,
    @SerialName(SOURCE) val source: Source,
) {

    @Serializable
    enum class Source {

        @SerialName(TRACK)
        Track,

        @SerialName(ALBUM)
        Album,

        @SerialName(PLAYLIST)
        Playlist,

        @SerialName(UNKNOWN)
        Unknown;

        companion object {
            const val TRACK = "track"
            const val ALBUM = "album"
            const val PLAYLIST = "playlist"
            const val UNKNOWN = "unknown"
        }
    }

    companion object {

        const val ID = "id"
        const val INDEX = "index"
        const val POSITION = "position"
        const val SOURCE = "source"

        val defaultValue: LocalResume
            get() = LocalResume(
                id = -1,
                index = -1,
                position = 0,
                source = Source.Unknown,
            )
    }
}
