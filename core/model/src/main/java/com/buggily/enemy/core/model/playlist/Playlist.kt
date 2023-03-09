package com.buggily.enemy.core.model.playlist

import com.buggily.enemy.core.model.UiInstant

data class Playlist(
    val id: Long = 0,
    val name: String,
    val creationInstant: UiInstant,
    val modificationInstant: UiInstant,
)
