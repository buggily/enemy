package com.buggily.enemy.data.theme.source.scheme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.buggily.enemy.local.preferences.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SchemeSource(
    private val dataStore: DataStore<Preferences>,
) : SchemeSourceable {

    override fun get(): Flow<Theme.Scheme> = dataStore.data.map { it[key] }.map {
        Theme.Scheme.get(it)
    }

    override suspend fun set(scheme: Theme.Scheme) {
        dataStore.edit { it[key] = scheme.identity }
    }

    private companion object {
        private const val identity = "scheme"
        private val key: Preferences.Key<String> = stringPreferencesKey(identity)
    }
}
