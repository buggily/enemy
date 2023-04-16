package com.buggily.enemy.local.preferences.dynamic

import com.buggily.enemy.local.preferences.LocalTheme
import kotlinx.coroutines.flow.Flow

interface LocalDynamicSourceable {
    fun get(): Flow<LocalTheme.Dynamic>
    suspend fun set(dynamic: LocalTheme.Dynamic)
}
