package com.buggily.enemy.domain.resume

data class ResumeUi(
    val id: Long,
    val position: Long,
    val source: Source,
) {

    sealed interface Source {
        data object Track : Source

        data class Album(
            override val index: Int,
        ) : Source

        data class Playlist(
            override val index: Int,
        ) : Source

        data object Unknown : Source

        val index: Int
            get() = 0
    }
}
