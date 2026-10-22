package com.example.netptunus_hafiz

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.netptunus_hafiz.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DatePicker dialog untuk Tanggal Lahir
        binding.etTglLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    binding.etTglLahir.setText("$dayOfMonth/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Tombol Selanjutnya
        binding.btnSelanjutnya.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val tglLahir = binding.etTglLahir.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            // Ambil nilai dari RadioButton yang terpilih
            val selectedGenderId = binding.rgJenisKelamin.checkedRadioButtonId
            val jenisKelamin = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else ""

            // 1. Validasi Input Tidak Boleh Kosong
            if (nama.isEmpty() || email.isEmpty() || tglLahir.isEmpty() ||
                jenisKelamin.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Validasi Konfirmasi Password
            if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Password tidak cocok!"
                return@setOnClickListener
            }

            // 3. Simpan ke SharedPreferences
            val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("NAMA", nama)
            editor.putString("EMAIL", email)
            editor.putString("TGL_LAHIR", tglLahir)
            editor.putString("JENIS_KELAMIN", jenisKelamin)
            editor.putString("USERNAME", username)
            editor.putString("PASSWORD", password)
            editor.apply()

            Toast.makeText(this, "Data tersimpan sementara", Toast.LENGTH_SHORT).show()

            // 4. Pindah ke Halaman Selanjutnya
            val intent = Intent(this, NextRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}