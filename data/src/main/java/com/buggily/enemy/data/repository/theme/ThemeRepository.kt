package com.buggily.enemy.data.repository.theme

import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.data.source.theme.ThemeSourceable
import kotlinx.coroutines.flow.Flow

class ThemeRepository(
    private val source: ThemeSourceable,
) : ThemeRepositable {

    override fun get(): Flow<Theme> = source.get()
    override suspend fun set(theme: Theme) = source.set(theme)
}
