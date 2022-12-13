package com.buggily.enemy.core.ui.ext

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.buggily.enemy.core.model.PagingPlaceholderKey

fun <Item : Any> LazyGridScope.items(
    items: LazyPagingItems<Item>,
    key: (Item) -> Any,
    itemContent: @Composable LazyGridItemScope.(Item?) -> Unit,
) {
    items(
        count = items.itemCount,
        key = { index: Int -> items.peek(index)?.let { key(it) } ?: PagingPlaceholderKey(index) },
    ) { itemContent(items[it]) }
}
