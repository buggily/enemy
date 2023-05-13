package com.buggily.enemy.core.ui.ext

import androidx.paging.compose.LazyPagingItems

val <Item : Any> LazyPagingItems<Item>.range: IntRange
    get() = 0 until itemCount

val <Item : Any> LazyPagingItems<Item>.firstIndex: Int
    get() = range.first

fun <Item : Any> LazyPagingItems<Item>.peekFirst(): Item? = try {
    peek(firstIndex)
} catch (e: IndexOutOfBoundsException) {
    null
}
