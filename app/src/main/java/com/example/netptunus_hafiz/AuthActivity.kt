package com.example.netptunus_hafiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.netptunus_hafiz.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // SharedPreferences untuk status sesi login
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Cek jika user sudah login
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val intent = Intent(this@AuthActivity, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        // SharedPreferences tempat penyimpanan data registrasi
        val userPref = getSharedPreferences("UserPref", MODE_PRIVATE)

        // Auto-fill Username dari registrasi (jika ada)
        val registeredUsername = userPref.getString("USERNAME", "")
        if (!registeredUsername.isNullOrEmpty()) {
            binding.etUsername.setText(registeredUsername)
        }

        // Logika Tombol Login sesuai Rule Soal 2
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Ambil username & password tersimpan di SharedPreferences
            val savedUsername = userPref.getString("USERNAME", "")
            val savedPassword = userPref.getString("PASSWORD", "")

            // Rule 1: username == password
            val rule1 = username.isNotEmpty() && username == password

            // Rule 2: username == username di SharedPreferences AND password == password di SharedPreferences
            val rule2 = username.isNotEmpty() && username == savedUsername && password == savedPassword

            // Jika memenuhi salah satu rule, izinkan masuk
            if (rule1 || rule2) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                val intent = Intent(this@AuthActivity, BaseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Username atau password salah. Silahkan coba lagi")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Navigasi ke halaman Register
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@AuthActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
