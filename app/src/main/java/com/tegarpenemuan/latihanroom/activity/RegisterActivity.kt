package com.tegarpenemuan.latihanroom.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tegarpenemuan.latihanroom.data.local.entity.UserEntity
import com.tegarpenemuan.latihanroom.database.MyNoteDatabase
import com.tegarpenemuan.latihanroom.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    private var db: MyNoteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyNoteDatabase.getInstance(applicationContext.applicationContext)

        binding.btnRegister.setOnClickListener {
            insertDataDatabase(
                UserEntity(
                    id = 0,
                    nama = binding.etNama.text.toString(),
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            )
        }
    }

    // function untuk insert data pada database
    private fun insertDataDatabase(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).async {
            val result = db?.userDao()?.insertUser(userEntity)
            this@RegisterActivity.runOnUiThread {
                if (result != 0L) {
                    Toast.makeText(applicationContext, "Register Berhasil", Toast.LENGTH_SHORT)
                        .show()
                    onBackPressed()
                } else {
                    Toast.makeText(applicationContext, "Register Gagal", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}