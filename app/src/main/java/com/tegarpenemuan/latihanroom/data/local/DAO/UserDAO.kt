package com.tegarpenemuan.latihanroom.data.local.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tegarpenemuan.latihanroom.data.local.entity.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM user WHERE email=:email AND password=:password LIMIT 1")
    fun getUser(email: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity): Long
}