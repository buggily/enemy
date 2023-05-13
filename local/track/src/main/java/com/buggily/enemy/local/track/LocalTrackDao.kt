package com.buggily.enemy.local.track

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    @Query(
        """
            SELECT * FROM ${LocalTrack.TABLE_NAME}
            WHERE ${LocalTrack.PLAYLIST_ID} = :playlistId AND
            ${LocalTrack.INDEX} = :index
        """
    )

    suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): LocalTrack?

    @Query(
        """
            SELECT MAX(${LocalTrack.INDEX}) FROM ${LocalTrack.TABLE_NAME}
            WHERE ${LocalTrack.PLAYLIST_ID} = :playlistId
        """
    )

    suspend fun getMaxIndexByPlaylistId(playlistId: Long): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: LocalTrack)

    @Delete
    suspend fun delete(track: LocalTrack)

    @Query(
        """
            DELETE FROM ${LocalTrack.TABLE_NAME}
            WHERE ${LocalTrack.PLAYLIST_ID} = :playlistId
        """
    )

    suspend fun deleteByPlaylistId(playlistId: Long)
}
