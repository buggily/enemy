package com.buggily.enemy.core.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class PositionalPagingSource<Value : Any> : PagingSource<PositionalPagingSource.Key, Value>() {

    data class Key(
        private val position: Int?,
        private val config: PagingConfig,
    ) {

        private val positionOrDefault: Int
            get() = position ?: defaultPosition

        private val isPositionDefault: Boolean
            get() = positionOrDefault == defaultPosition

        val limit: Int
            get() = with(config) { if (isPositionDefault) initialLoadSize else pageSize }

        val offset: Int
            get() {
                if (isPositionDefault) return positionOrDefault
                return with(config) { initialLoadSize + pageSize * positionOrDefault.dec() }
            }

        fun getPrev(): Key? = get(
            isLiminal = isPositionDefault,
            operator = Int::dec,
        )

        fun getNext(data: List<*>): Key? = get(
            isLiminal = data.isEmpty(),
            operator = Int::inc,
        )

        private fun get(
            isLiminal: Boolean,
            operator: (Int) -> Int,
        ): Key? {
            if (isLiminal) return null

            return Key(
                position = operator(positionOrDefault),
                config = config,
            )
        }

        companion object {
            fun getDefault(config: PagingConfig): Key = Key(
                position = null,
                config = config,
            )

            private const val defaultPosition = 0
        }
    }

    override fun getRefreshKey(
        state: PagingState<Key, Value>,
    ): Key = with(state) {
        Key(
            position = anchorPosition,
            config = config,
        )
    }
}
