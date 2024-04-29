package com.buggily.enemy.local.track

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalTrackDao {

    @Query(
        """
            SELECT * FROM ${LocalTrack.TABLE_NAME}
            ORDER BY ${LocalTrack.LAST_PLAY_INSTANT} DESC
        """
    )

    fun getPagingByRecency(): PagingSource<Int, LocalTrack>

    @Query(
        """
            SELECT * FROM ${LocalTrack.TABLE_NAME}
            ORDER BY ${LocalTrack.PLAYS} DESC,
                ${LocalTrack.LAST_PLAY_INSTANT} DESC
        """
    )

    fun getPagingByPopularity(): PagingSource<Int, LocalTrack>

    @Query(
        """
            SELECT * FROM ${LocalTrack.TABLE_NAME}
            WHERE ${LocalTrack.ID} = :id
        """
    )

    suspend fun getById(id: Long): LocalTrack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: LocalTrack)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(track: LocalTrack)
}
