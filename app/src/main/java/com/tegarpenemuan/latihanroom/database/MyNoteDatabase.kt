package com.tegarpenemuan.latihanroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tegarpenemuan.latihanroom.data.local.DAO.NoteDAO
import com.tegarpenemuan.latihanroom.data.local.DAO.UserDAO
import com.tegarpenemuan.latihanroom.data.local.entity.NoteEntity
import com.tegarpenemuan.latihanroom.data.local.entity.UserEntity

@Database(entities = [NoteEntity::class,UserEntity::class], version = 2)
abstract class MyNoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDAO
    abstract fun userDao(): UserDAO

    companion object {
        private const val DB_NAME = "MyCatatan.db"

        @Volatile
        private var INSTANCE: MyNoteDatabase? = null

        fun getInstance(context: Context): MyNoteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): MyNoteDatabase {
            return Room.databaseBuilder(context, MyNoteDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}