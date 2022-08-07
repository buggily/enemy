package com.buggily.enemy.data

data class Album(
    val id: Long?,
    val name: String?,
    val artist: Artist,
) {

    data class Artist(
        val id: Long?,
        val name: String?,
    )
}
