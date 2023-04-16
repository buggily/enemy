package com.buggily.enemy.local.preferences.scheme

import com.buggily.enemy.local.preferences.LocalTheme
import kotlinx.coroutines.flow.Flow

interface LocalSchemeSourceable {
    fun get(): Flow<LocalTheme.Scheme>
    suspend fun set(scheme: LocalTheme.Scheme)
}
