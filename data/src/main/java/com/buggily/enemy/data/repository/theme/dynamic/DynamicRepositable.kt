package com.buggily.enemy.data.repository.theme.dynamic

import com.buggily.enemy.data.theme.Theme
import kotlinx.coroutines.flow.Flow

interface DynamicRepositable {
    fun get(): Flow<Theme.Dynamic>
    suspend fun set(dynamic: Theme.Dynamic)
}
