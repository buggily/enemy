package com.buggily.enemy.data.source.theme

import com.buggily.enemy.data.theme.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeSourceable {
    fun get(): Flow<Theme>
    suspend fun set(theme: Theme)
}
