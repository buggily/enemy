package com.buggily.enemy.data.theme.source.dynamic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.buggily.enemy.local.preferences.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DynamicSource(
    private val dataStore: DataStore<Preferences>,
) : DynamicSourceable {

    override fun get(): Flow<Theme.Dynamic> = dataStore.data.map { it[key] }.map {
        Theme.Dynamic.get(it)
    }

    override suspend fun set(dynamic: Theme.Dynamic) {
        dataStore.edit { it[key] = dynamic.identity }
    }

    private companion object {
        private const val identity = "dynamic"
        private val key: Preferences.Key<String> = stringPreferencesKey(identity)
    }
}
