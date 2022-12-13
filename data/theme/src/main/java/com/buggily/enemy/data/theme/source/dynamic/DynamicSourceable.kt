package com.buggily.enemy.data.theme.source.dynamic

import com.buggily.enemy.local.preferences.Theme
import kotlinx.coroutines.flow.Flow

interface DynamicSourceable {
    fun get(): Flow<Theme.Dynamic>
    suspend fun set(dynamic: Theme.Dynamic)
}
