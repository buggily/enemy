package com.buggily.enemy.local.resume

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

internal class LocalResumeSource(
    private val dataStore: DataStore<LocalResume>,
) : LocalResumeSourceable {

    override fun get(): Flow<LocalResume> = dataStore.data

    override suspend fun set(
        id: Long,
        index: Int,
        source: LocalResume.Source,
    ) { dataStore.updateData { it.copy(id = id, index = index, position = 0, source = source) } }

    override suspend fun setIdAndIndex(
        id: Long,
        index: Int,
    ) { dataStore.updateData { it.copy(id = id, index = index, position = 0) } }

    override suspend fun setPosition(
        position: Long,
    ) { dataStore.updateData { it.copy(position = position) } }
}
