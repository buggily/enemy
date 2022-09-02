package com.buggily.enemy.data.source.theme.dynamic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.buggily.enemy.data.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DynamicSource(
    private val dataStore: DataStore<Preferences>,
) : DynamicSourceable {

    override fun get(): Flow<Theme.Dynamic> = dataStore.data.map { it[KEY] }.map {
        Theme.Dynamic.get(it)
    }

    override suspend fun set(dynamic: Theme.Dynamic) {
        dataStore.edit { it[KEY] = dynamic.identity }
    }

    private companion object {
        private const val IDENTITY = "dynamic"
        private val KEY: Preferences.Key<String> = stringPreferencesKey(IDENTITY)
    }
}
