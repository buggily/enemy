package com.buggily.enemy.paging

import java.io.Serializable

data class PagingPlaceholderKey(
    private val index: Int,
) : Serializable
