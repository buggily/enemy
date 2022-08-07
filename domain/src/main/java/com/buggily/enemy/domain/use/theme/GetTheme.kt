package com.buggily.enemy.domain.use.theme

import com.buggily.enemy.data.repository.theme.ThemeRepositable
import com.buggily.enemy.domain.map.theme.ThemeMapper
import com.buggily.enemy.domain.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTheme(
    private val repository: ThemeRepositable,
    private val mapper: ThemeMapper,
) {

    operator fun invoke(): Flow<Theme> = repository.get().map {
        mapper.mapTo(it)
    }
}
