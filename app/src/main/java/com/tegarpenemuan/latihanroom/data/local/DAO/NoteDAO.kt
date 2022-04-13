package com.tegarpenemuan.latihanroom.data.local.DAO

import androidx.room.*
import com.tegarpenemuan.latihanroom.data.local.entity.NoteEntity

@Dao
interface NoteDAO {

    @Query("SELECT * FROM catatan")
    fun getNote(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity): Long

    @Update
    fun updateNote(noteEntity: NoteEntity): Int

    @Delete
    fun deleteNote(noteEntity: NoteEntity): Int
}