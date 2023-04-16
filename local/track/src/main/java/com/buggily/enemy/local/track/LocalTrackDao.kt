package com.buggily.enemy.local.track

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalTrackDao {

    @Query(
        """
            SELECT * FROM ${LocalTrack.TABLE_NAME}
            WHERE ${LocalTrack.PLAYLIST_ID} = :playlistId
            ORDER BY ${LocalTrack.INDEX} ASC
        """
    )

    fun getPagingByPlaylistId(playlistId: Long): PagingSource<Int, LocalTrack>

    @Query(
        """
            SELECT * FROM ${LocalTrack.TABLE_NAME}
            WHERE ${LocalTrack.PLAYLIST_ID} = :playlistId
            ORDER BY ${LocalTrack.INDEX} ASC
        """
    )

    suspend fun getByPlaylistId(playlistId: Long): List<LocalTrack>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: List<LocalTrack>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(tracks: List<LocalTrack>)

    @Delete
    fun delete(tracks: List<LocalTrack>)


}
