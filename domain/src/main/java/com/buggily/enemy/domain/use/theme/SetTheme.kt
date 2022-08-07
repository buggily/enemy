package com.buggily.enemy.domain.use.theme

import com.buggily.enemy.data.repository.theme.ThemeRepositable
import com.buggily.enemy.domain.map.theme.ThemeMapper
import com.buggily.enemy.domain.theme.Theme

class SetTheme(
    private val repository: ThemeRepositable,
    private val mapper: ThemeMapper,
) {

    suspend operator fun invoke(theme: Theme) = mapper.mapFrom(theme).let {
        repository.set(it)
    }
}
