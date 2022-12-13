package com.buggily.enemy.data.theme.source.scheme

import com.buggily.enemy.local.preferences.Theme
import kotlinx.coroutines.flow.Flow

interface SchemeSourceable {
    fun get(): Flow<Theme.Scheme>
    suspend fun set(scheme: Theme.Scheme)
}
