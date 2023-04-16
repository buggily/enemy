package com.buggily.enemy.local.preferences.scheme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.buggily.enemy.local.preferences.LocalTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LocalSchemeSource(
    private val preferencesDataStore: DataStore<Preferences>,
) : LocalSchemeSourceable {

    override fun get(): Flow<LocalTheme.Scheme> = preferencesDataStore.data.map {
        LocalTheme.Scheme.get(it[key])
    }

    override suspend fun set(scheme: LocalTheme.Scheme) {
        preferencesDataStore.edit { it[key] = scheme.identity }
    }

    private companion object {
        private const val identity = "scheme"
        private val key: Preferences.Key<String> = stringPreferencesKey(identity)
    }
}
