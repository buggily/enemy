package com.buggily.enemy.data.theme.repository

import com.buggily.enemy.core.model.theme.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeRepositable {

    fun get(): Flow<Theme>

    suspend fun set(theme: Theme)
    suspend fun set(scheme: Theme.Scheme)
    suspend fun set(dynamic: Theme.Dynamic)
}
