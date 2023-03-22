package com.buggily.enemy.local.playlist

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlaylistDao {

    @Query(
        """
           SELECT * FROM ${Playlist.TABLE_NAME}
           WHERE ${Playlist.NAME} LIKE :search
           ORDER BY ${Playlist.MODIFICATION_INSTANT} DESC
        """
    )

    fun getPaging(search: String): PagingSource<Int, Playlist>

    @Query(
        """
           SELECT * FROM ${Playlist.TABLE_NAME}
           WHERE ${Playlist.ID} = :id
        """
    )

    suspend fun getById(id: Long): Playlist

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: Playlist)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(playlist: Playlist)

    @Delete
    suspend fun delete(playlist: Playlist)
}
