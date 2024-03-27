package com.buggily.enemy.local.track

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocalTrackSourceable {

    fun getPagingByRecency(): Flow<PagingData<LocalTrack>>
    suspend fun getById(id: Long): LocalTrack?

    suspend fun insert(track: LocalTrack)
    suspend fun update(track: LocalTrack)
}
