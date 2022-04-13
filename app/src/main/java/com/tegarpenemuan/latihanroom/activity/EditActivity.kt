package com.tegarpenemuan.latihanroom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tegarpenemuan.latihanroom.data.local.entity.NoteEntity
import com.tegarpenemuan.latihanroom.database.MyNoteDatabase
import com.tegarpenemuan.latihanroom.databinding.ActivityEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private var db: MyNoteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyNoteDatabase.getInstance(applicationContext.applicationContext)

        val id =intent.getStringExtra("id")
        val judul =intent.getStringExtra("judul")
        val catatan =intent.getStringExtra("catatan")

        binding.etJudul.setText(judul)
        binding.etCatatan.setText(catatan)

        binding.btnEdit.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db?.noteDao()?.updateNote(
                    NoteEntity(
                        id = id.toString().toInt(),
                        judul = binding.etJudul.text.toString(),
                        catatan = binding.etCatatan.text.toString()
                    )
                )
            }
            Toast.makeText(applicationContext, "Data Berhasil Diupdate", Toast.LENGTH_SHORT)
                .show()
            onBackPressed()
        }
    }
}