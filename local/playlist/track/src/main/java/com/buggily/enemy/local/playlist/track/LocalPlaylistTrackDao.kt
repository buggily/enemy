package com.buggily.enemy.local.playlist.track

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalPlaylistTrackDao {

    @Query(
        """
            SELECT * FROM ${LocalPlaylistTrack.TABLE_NAME}
            WHERE ${LocalPlaylistTrack.PLAYLIST_ID} = :playlistId
            ORDER BY ${LocalPlaylistTrack.INDEX} ASC
        """
    )

    fun getPagingByPlaylistId(playlistId: Long): PagingSource<Int, LocalPlaylistTrack>

    @Query(
        """
            SELECT * FROM ${LocalPlaylistTrack.TABLE_NAME}
            WHERE ${LocalPlaylistTrack.PLAYLIST_ID} = :playlistId
            ORDER BY ${LocalPlaylistTrack.INDEX} ASC
        """
    )

    suspend fun getByPlaylistId(playlistId: Long): List<LocalPlaylistTrack>

    @Query(
        """
            SELECT * FROM ${LocalPlaylistTrack.TABLE_NAME}
            WHERE ${LocalPlaylistTrack.PLAYLIST_ID} = :playlistId
                AND ${LocalPlaylistTrack.INDEX} = :index
        """
    )

    suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): LocalPlaylistTrack?

    @Query(
        """
            SELECT MAX(${LocalPlaylistTrack.INDEX}) FROM ${LocalPlaylistTrack.TABLE_NAME}
            WHERE ${LocalPlaylistTrack.PLAYLIST_ID} = :playlistId
        """
    )

    suspend fun getMaxIndexByPlaylistId(playlistId: Long): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: LocalPlaylistTrack)

    @Query(
        """
            DELETE FROM ${LocalPlaylistTrack.TABLE_NAME}
            WHERE ${LocalPlaylistTrack.ID} = :id 
                AND ${LocalPlaylistTrack.PLAYLIST_ID} = :playlistId
                AND ${LocalPlaylistTrack.INDEX} = :index
        """
    )

    suspend fun deleteByIdAndPlaylistIdAndIndex(
        id: Long,
        playlistId: Long,
        index: Int,
    )

    @Query(
        """
            DELETE FROM ${LocalPlaylistTrack.TABLE_NAME}
            WHERE ${LocalPlaylistTrack.PLAYLIST_ID} = :playlistId
        """
    )

    suspend fun deleteByPlaylistId(playlistId: Long)
}
