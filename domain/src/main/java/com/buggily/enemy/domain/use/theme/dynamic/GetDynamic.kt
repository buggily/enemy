package com.buggily.enemy.domain.use.theme.dynamic

import com.buggily.enemy.data.repository.track.dynamic.DynamicRepositable
import com.buggily.enemy.domain.map.theme.dynamic.DynamicMapper
import com.buggily.enemy.domain.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDynamic(
    private val repository: DynamicRepositable,
    private val mapper: DynamicMapper,
) {

    operator fun invoke(): Flow<Theme.Dynamic> = repository.get().map {
        mapper.mapTo(it)
    }
}
