package com.buggily.enemy.core.ui.ext

import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.range: IntRange
    get() = 0 until itemCount

val <T : Any> LazyPagingItems<T>.firstIndex: Int
    get() = range.first

fun <T : Any> LazyPagingItems<T>.peekFirst(): T? = try {
    peek(firstIndex)
} catch (e: IndexOutOfBoundsException) {
    null
}
