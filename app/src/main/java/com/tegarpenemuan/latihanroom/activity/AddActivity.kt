package com.tegarpenemuan.latihanroom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tegarpenemuan.latihanroom.data.local.entity.NoteEntity
import com.tegarpenemuan.latihanroom.database.MyNoteDatabase
import com.tegarpenemuan.latihanroom.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var db: MyNoteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyNoteDatabase.getInstance(applicationContext.applicationContext)

        binding.btnAdd.setOnClickListener {
            insertDataDatabase(
                NoteEntity(
                    id = 0,
                    judul = binding.etJudul.text.toString(),
                    catatan = binding.etCatatan.text.toString()
                )
            )
        }
    }

    // function untuk insert data pada database
    private fun insertDataDatabase(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).async {
            val result = db?.noteDao()?.insertNote(noteEntity)
            this@AddActivity.runOnUiThread {
                if (result != 0L) {
                    Toast.makeText(applicationContext, "Success insert", Toast.LENGTH_SHORT)
                        .show()
                    onBackPressed()
                } else {
                    Toast.makeText(applicationContext, "Failure insert", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}