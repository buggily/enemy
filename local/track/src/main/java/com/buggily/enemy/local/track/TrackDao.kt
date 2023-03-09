package com.buggily.enemy.local.track

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TrackDao {

    @Query(
        """
            SELECT * FROM ${Track.TABLE_NAME}
            WHERE ${Track.PLAYLIST_ID} = :playlistId
            ORDER BY ${Track.INDEX} ASC
        """
    )

    suspend fun getTracksByPlaylistId(playlistId: Long): List<Track>

    @Query(
        """
            SELECT * FROM ${Track.TABLE_NAME}
            WHERE ${Track.PLAYLIST_ID} = :playlistId
            ORDER BY ${Track.INDEX} ASC
        """
    )

    fun getTrackPagingByPlaylistId(playlistId: Long): PagingSource<Int, Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: List<Track>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(tracks: List<Track>)

    @Delete
    fun delete(tracks: List<Track>)


}
