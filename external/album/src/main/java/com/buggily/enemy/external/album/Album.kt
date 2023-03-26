package com.buggily.enemy.external.album

data class Album(
    val id: Long,
    val name: String,
    val artist: Artist,
) {

    data class Artist(
        val id: Long,
        val name: String,
    )
}
