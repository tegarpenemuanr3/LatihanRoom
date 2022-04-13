package com.tegarpenemuan.latihanroom.data.local.DAO

import androidx.room.*
import com.tegarpenemuan.latihanroom.data.local.entity.NoteEntity

@Dao
interface NoteDAO {

    @Query("SELECT * FROM catatan")
    fun getMessage(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(noteEntity: NoteEntity): Long

    @Update
    fun updateMessage(noteEntity: NoteEntity): Int

    @Delete
    fun deleteMessage(noteEntity: NoteEntity): Int
}