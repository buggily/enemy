package com.buggily.enemy.data.source.search

import androidx.datastore.core.DataStore
import com.buggily.enemy.data.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class SearchSource(
    private val dataStore: DataStore<Search>,
) : SearchSourceable {

    override fun get(): Flow<Search> = dataStore.data.catch {
        if (it is IOException) emit(Search.getDefaultInstance())
    }

    override suspend fun set(search: Search) {
        dataStore.updateData { search }
    }
}
