package com.buggily.enemy.domain.playlist

import kotlinx.datetime.Instant

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
