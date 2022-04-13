package com.tegarpenemuan.latihanroom.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tegarpenemuan.latihanroom.R
import com.tegarpenemuan.latihanroom.adapter.NoteAdapter
import com.tegarpenemuan.latihanroom.data.local.entity.NoteEntity
import com.tegarpenemuan.latihanroom.database.MyNoteDatabase
import com.tegarpenemuan.latihanroom.databinding.ActivityMainBinding
import com.tegarpenemuan.latihanroom.model.NoteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private var db: MyNoteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = MyNoteDatabase.getInstance(applicationContext.applicationContext)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(applicationContext, AddActivity::class.java))
        }

        adapter = NoteAdapter(
            listener = object : NoteAdapter.EventListener {
                override fun onClick(item: NoteModel) {
                    val dialog = Dialog(this@MainActivity)
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setContentView(R.layout.dialog_opsi)

                    val BtnEdit = dialog.findViewById(R.id.btnEditData) as Button
                    val BtnHapus = dialog.findViewById(R.id.btnHapusData) as Button

                    BtnEdit.setOnClickListener {
                        startActivity(Intent(applicationContext, EditActivity::class.java))
                    }
                    BtnHapus.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            db?.noteDao()?.deleteNote(
                                NoteEntity(
                                    id = item.id,
                                    judul = item.judul,
                                    catatan = item.catatan
                                )
                            )
                        }

                        dialog.dismiss()
                        loadDataDatabase()
                        Toast.makeText(
                            applicationContext,
                            "Data Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dialog.show()
                }
            },
            notes = emptyList()
        )
    }

    override fun onStart() {
        super.onStart()
        binding.rvNote.layoutManager = LinearLayoutManager(this)
        binding.rvNote.adapter = adapter
        binding.rvNote.hasFixedSize()
        loadDataDatabase()
    }

    private fun loadDataDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val note = db?.noteDao()?.getNote()
            Log.d("MainActivity", "db:${note}")
            this@MainActivity.runOnUiThread {
                note?.let {
                    val note = it.map {
                        NoteModel(
                            id = it.id,
                            judul = it.judul,
                            catatan = it.catatan
                        )
                    }
                    adapter.updateList(note)
                }
            }
        }
    }
}