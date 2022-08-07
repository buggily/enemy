package com.buggily.enemy.domain.use.theme.dynamic

import com.buggily.enemy.data.repository.track.dynamic.DynamicRepositable
import com.buggily.enemy.domain.map.theme.dynamic.DynamicMapper
import com.buggily.enemy.domain.theme.Theme

class SetDynamic(
    private val repository: DynamicRepositable,
    private val mapper: DynamicMapper,
) {

    suspend operator fun invoke(dynamic: Theme.Dynamic) = mapper.mapFrom(dynamic).let {
        repository.set(it)
    }
}
