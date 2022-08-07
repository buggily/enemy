package com.buggily.enemy.data.source.theme.dynamic

import com.buggily.enemy.data.Theme
import kotlinx.coroutines.flow.Flow

interface DynamicSourceable {
    fun get(): Flow<Theme.Dynamic>
    suspend fun set(dynamic: Theme.Dynamic)
}
