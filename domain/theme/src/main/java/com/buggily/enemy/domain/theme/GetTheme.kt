package com.buggily.enemy.domain.theme

import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.data.theme.repository.ThemeRepositable
import kotlinx.coroutines.flow.Flow

class GetTheme(
    private val repository: ThemeRepositable,
) {

    operator fun invoke(): Flow<Theme> = repository.get()
}
