package com.buggily.enemy.local.playlist

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalPlaylistDao {

    @Query(
        """
           SELECT * FROM ${LocalPlaylist.TABLE_NAME}
           WHERE ${LocalPlaylist.NAME} LIKE :search
           ORDER BY ${LocalPlaylist.MODIFICATION_INSTANT} DESC
        """
    )

    fun getPaging(search: String): PagingSource<Int, LocalPlaylist>

    @Query(
        """
           SELECT * FROM ${LocalPlaylist.TABLE_NAME}
           WHERE ${LocalPlaylist.ID} = :id
        """
    )

    suspend fun getById(id: Long): LocalPlaylist

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: LocalPlaylist)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(playlist: LocalPlaylist)

    @Delete
    suspend fun delete(playlist: LocalPlaylist)
}
