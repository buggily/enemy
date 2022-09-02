package com.buggily.enemy.data.source.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.buggily.enemy.data.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeSource(
    private val dataStore: DataStore<Preferences>,
) : ThemeSourceable {

    override fun get(): Flow<Theme> = dataStore.data.map { it[KEY] }.map {
        Theme.get(it)
    }

    override suspend fun set(theme: Theme) {
        dataStore.edit { it[KEY] = theme.identity }
    }

    private companion object {
        private const val IDENTITY = "theme"
        private val KEY: Preferences.Key<String> = stringPreferencesKey(IDENTITY)
    }
}
