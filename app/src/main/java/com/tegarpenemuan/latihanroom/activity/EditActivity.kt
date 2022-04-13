package com.tegarpenemuan.latihanroom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tegarpenemuan.latihanroom.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}