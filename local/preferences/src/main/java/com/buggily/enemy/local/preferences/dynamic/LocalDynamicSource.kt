package com.buggily.enemy.local.preferences.dynamic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.buggily.enemy.local.preferences.LocalTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LocalDynamicSource(
    private val preferencesDataStore: DataStore<Preferences>,
) : LocalDynamicSourceable {

    override fun get(): Flow<LocalTheme.Dynamic> = preferencesDataStore.data.map {
        LocalTheme.Dynamic.get(it[key])
    }

    override suspend fun set(dynamic: LocalTheme.Dynamic) {
        preferencesDataStore.edit { it[key] = dynamic.identity }
    }

    private companion object {
        private const val identity = "dynamic"
        private val key: Preferences.Key<String> = stringPreferencesKey(identity)
    }
}
