package com.example.netptunus_hafiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.netptunus_hafiz.databinding.ActivityNextRegisterBinding

class NextRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNextRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("NAMA", "") ?: ""
        val email = sharedPref.getString("EMAIL", "") ?: ""
        val tglLahir = sharedPref.getString("TGL_LAHIR", "") ?: ""
        val jenisKelamin = sharedPref.getString("JENIS_KELAMIN", "") ?: ""
        val username = sharedPref.getString("USERNAME", "") ?: ""
        val password = sharedPref.getString("PASSWORD", "") ?: ""
        val confirmPassword = sharedPref.getString("PASSWORD", "") ?: "" // Ambil nilai password/confirm

        // Tampilkan seluruh data yang ada di SharedPreferences
        binding.tvNama.text = "Nama: $nama"
        binding.tvEmail.text = "Email: $email"
        binding.tvTglLahir.text = "Tanggal Lahir: $tglLahir"
        binding.tvJenisKelamin.text = "Jenis Kelamin: $jenisKelamin"
        binding.tvUsername.text = "Username: $username"
        binding.tvPassword.text = "Password: $password"
        binding.tvConfirmPassword.text = "Confirm Password: $confirmPassword"

        // Tombol Submit untuk Validasi
        binding.btnSubmit.setOnClickListener {
            if (nama.isEmpty() || email.isEmpty() || tglLahir.isEmpty() ||
                jenisKelamin.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Validasi Gagal: Semua data di SP tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Validasi Gagal: Password dan Confirm Password tidak sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Jika berhasil melewati validasi
            AlertDialog.Builder(this)
                .setTitle("Registrasi Berhasil")
                .setMessage("Data kamu telah terverifikasi dan berhasil didaftarkan!")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setCancelable(false)
                .show()
        }

        // Tombol Kembali ke halaman registrasi untuk memperbaiki isian[cite: 1]
        binding.btnKembali.setOnClickListener {
            finish()
        }
    }
}