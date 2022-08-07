package com.buggily.enemy.data.repository.theme

import com.buggily.enemy.data.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeRepositable {
    fun get(): Flow<Theme>
    suspend fun set(theme: Theme)
}
