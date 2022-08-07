package com.buggily.enemy.data.repository.track.dynamic

import com.buggily.enemy.data.Theme
import kotlinx.coroutines.flow.Flow

interface DynamicRepositable {
    fun get(): Flow<Theme.Dynamic>
    suspend fun set(dynamic: Theme.Dynamic)
}
