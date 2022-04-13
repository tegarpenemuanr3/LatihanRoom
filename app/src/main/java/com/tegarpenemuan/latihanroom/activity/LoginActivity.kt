package com.tegarpenemuan.latihanroom.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tegarpenemuan.latihanroom.database.MyNoteDatabase
import com.tegarpenemuan.latihanroom.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var db: MyNoteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyNoteDatabase.getInstance(applicationContext.applicationContext)

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            getUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    fun getUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getUser(email = email, password = password)
            runOnUiThread {
                user?.let {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } ?: run {
                    Toast.makeText(this@LoginActivity, "User tidak ditemukan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}