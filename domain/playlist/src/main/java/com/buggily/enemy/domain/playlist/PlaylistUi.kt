package com.buggily.enemy.domain.playlist

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class PlaylistUi(
    val id: Long,
    val name: String,
    val modification: Modification,
) {

    val nameText: String
        get() = name

    val modificationText: String
        get() = modification.text

    data class Modification(
        val instant: Instant,
        val text: String,
    )
}
