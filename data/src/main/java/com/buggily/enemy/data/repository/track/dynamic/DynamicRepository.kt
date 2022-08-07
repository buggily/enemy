package com.buggily.enemy.data.repository.track.dynamic

import com.buggily.enemy.data.Theme
import com.buggily.enemy.data.source.theme.dynamic.DynamicSourceable
import kotlinx.coroutines.flow.Flow

class DynamicRepository(
    private val source: DynamicSourceable,
) : DynamicRepositable {

    override fun get(): Flow<Theme.Dynamic> = source.get()
    override suspend fun set(dynamic: Theme.Dynamic) = source.set(dynamic)
}
