package com.buggily.enemy.core.data

interface Playlistable {
    val id: Long
    val name: String
    val creationInstant: Instantable
    val modificationInstant: Instantable
}
