package com.buggily.enemy.ext

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.buggily.enemy.paging.PagingPlaceholderKey

fun <Item : Any> LazyListScope.nullableItems(
    items: LazyPagingItems<Item>,
    key: (Item) -> Any?,
    itemContent: @Composable LazyItemScope.(Item?) -> Unit,
) {
    items(
        count = items.itemCount,
        key = getKey(
            key = key,
            items = items,
        ),
    ) { itemContent(items[it]) }
}

fun <Item : Any> LazyGridScope.nullableItems(
    items: LazyPagingItems<Item>,
    key: (Item) -> Any?,
    itemContent: @Composable LazyGridItemScope.(Item?) -> Unit,
) {
    items(
        count = items.itemCount,
        key = getKey(
            key = key,
            items = items,
        ),
    ) { itemContent(items[it]) }
}

private fun <Item : Any> getKey(
    key: (Item) -> Any?,
    items: LazyPagingItems<Item>,
): (Int) -> Any = { index ->
    items.peek(index)?.let { key(it) } ?: PagingPlaceholderKey(index)
}
