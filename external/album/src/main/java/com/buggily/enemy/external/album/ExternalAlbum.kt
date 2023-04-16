package com.buggily.enemy.external.album

data class ExternalAlbum(
    val id: Long,
    val name: String,
    val artist: Artist,
) {

    data class Artist(
        val id: Long,
        val name: String,
    )
}
