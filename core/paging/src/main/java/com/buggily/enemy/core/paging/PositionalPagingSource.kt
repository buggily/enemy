package com.buggily.enemy.core.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.buggily.enemy.core.ext.isNonPositive

abstract class PositionalPagingSource<Value : Any> :
    PagingSource<PositionalPagingSource.Key, Value>() {

    data class Key(
        private val position: Int?,
        private val config: PagingConfig,
    ) {

        private val positionOrDefault: Int
            get() = position ?: DEFAULT_POSITION

        private val isPositionDefault: Boolean
            get() = positionOrDefault == DEFAULT_POSITION

        val limit: Int
            get() = with(config) { if (isPositionDefault) initialLoadSize else pageSize }

        val offset: Int
            get() {
                if (isPositionDefault) return positionOrDefault
                return with(config) { initialLoadSize + pageSize * positionOrDefault.dec() }
            }

        fun getPrev(): Key? = get(
            isLiminal = isPositionDefault,
            operation = Int::dec,
        )

        fun getNext(size: Int): Key? = get(
            isLiminal = size.isNonPositive,
            operation = Int::inc,
        )

        private fun get(
            isLiminal: Boolean,
            operation: (Int) -> Int,
        ): Key? {
            if (isLiminal) return null

            return Key(
                position = operation(positionOrDefault),
                config = config,
            )
        }

        companion object {
            fun getDefault(config: PagingConfig): Key = Key(
                position = null,
                config = config,
            )

            private const val DEFAULT_POSITION = 0
        }
    }

    override fun getRefreshKey(
        state: PagingState<Key, Value>,
    ): Key = Key(
        position = state.anchorPosition,
        config = state.config,
    )
}
