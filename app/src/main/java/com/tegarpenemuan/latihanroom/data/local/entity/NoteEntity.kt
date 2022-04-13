package com.tegarpenemuan.latihanroom.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "catatan")
@Parcelize
data class  NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "judul") var judul: String,
    @ColumnInfo(name = "catatan") var catatan: String
) : Parcelable