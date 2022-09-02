package com.buggily.enemy.domain.search

data class Search(
    val value: String,
    val isVisible: Boolean,
) {

    companion object {
        val default: Search
            get() = Search(
                value = String(),
                isVisible = false,
            )
    }
}
