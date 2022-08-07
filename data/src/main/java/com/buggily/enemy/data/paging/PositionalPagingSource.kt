package com.buggily.enemy.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class PositionalPagingSource<Value : Any> : PagingSource<PositionalPagingSource.Key, Value>() {

    data class Key(
        private val position: Int?,
        private val config: PagingConfig,
    ) {

        private val positionOrDefault: Int
            get() = position ?: POSITION_DEFAULT

        private val isPositionDefault: Boolean
            get() = positionOrDefault == POSITION_DEFAULT

        val limit: Int
            get() {
                return config.run { if (isPositionDefault) initialLoadSize else pageSize }
            }

        val offset: Int
            get() {
                if (isPositionDefault) return positionOrDefault
                return config.run { initialLoadSize + pageSize * positionOrDefault.dec() }
            }

        fun getPrev(): Key? {
            val isFirst: Boolean = isPositionDefault

            return get(
                isLiminal = isFirst,
                operator = Int::dec,
            )
        }

        fun getNext(data: List<*>): Key? {
            val isLast: Boolean = data.isEmpty()

            return get(
                isLiminal = isLast,
                operator = Int::inc,
            )
        }

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

            private const val POSITION_DEFAULT = 0
        }
    }

    override fun getRefreshKey(
        state: PagingState<Key, Value>,
    ): Key = Key(
        position = state.anchorPosition,
        config = state.config,
    )
}
