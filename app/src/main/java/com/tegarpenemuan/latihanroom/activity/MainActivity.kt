package com.tegarpenemuan.latihanroom.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tegarpenemuan.latihanroom.adapter.NoteAdapter
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
                   Toast.makeText(applicationContext,"${item.judul}",Toast.LENGTH_SHORT).show()
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
            Log.d("MainActivity","db:${note}")
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