package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.data.theme.ThemeRepositable
import kotlinx.coroutines.flow.Flow

class GetTheme(
    private val repository: ThemeRepositable,
) {

    operator fun invoke(): Flow<Theme> = repository.get()
}
