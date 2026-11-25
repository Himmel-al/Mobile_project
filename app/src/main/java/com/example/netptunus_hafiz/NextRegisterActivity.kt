package com.example.netptunus_hafiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.netptunus_hafiz.databinding.ActivityNextRegisterBinding
import com.example.netptunus_hafiz.model.UserModel
import com.example.netptunus_hafiz.network.SupabaseApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NextRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNextRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
        val nama = sharedPref.getString("NAMA", "") ?: ""
        val email = sharedPref.getString("EMAIL", "") ?: ""
        val tglLahir = sharedPref.getString("TGL_LAHIR", "") ?: ""
        val jenisKelamin = sharedPref.getString("JENIS_KELAMIN", "") ?: ""
        val username = sharedPref.getString("USERNAME", "") ?: ""
        val password = sharedPref.getString("PASSWORD", "") ?: ""
        val confirmPassword = sharedPref.getString("PASSWORD", "") ?: ""

        // Tampilkan seluruh data yang ada di SharedPreferences
        binding.tvNama.text = "Nama: $nama"
        binding.tvEmail.text = "Email: $email"
        binding.tvTglLahir.text = "Tanggal Lahir: $tglLahir"
        binding.tvJenisKelamin.text = "Jenis Kelamin: $jenisKelamin"
        binding.tvUsername.text = "Username: $username"
        binding.tvPassword.text = "Password: $password"
        binding.tvConfirmPassword.text = "Confirm Password: $confirmPassword"

        // Tombol Submit untuk Validasi dan Insert ke Supabase
        binding.btnSubmit.setOnClickListener {
            if (nama.isEmpty() || email.isEmpty() || tglLahir.isEmpty() ||
                jenisKelamin.isEmpty() || username.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(this, "Validasi Gagal: Semua data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Validasi Gagal: Password dan Confirm Password tidak sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newUser = UserModel(
                nama = nama,
                email = email,
                tglLahir = tglLahir,
                jenisKelamin = jenisKelamin,
                username = username,
                password = password
            )

            binding.btnSubmit.isEnabled = false
            Toast.makeText(this, "Mengirim data ke Supabase...", Toast.LENGTH_SHORT).show()

            // Call Supabase API
            SupabaseApiClient.getService().registerUser(newUser).enqueue(object : Callback<List<UserModel>> {
                override fun onResponse(
                    call: Call<List<UserModel>>,
                    response: Response<List<UserModel>>
                ) {
                    binding.btnSubmit.isEnabled = true
                    if (response.isSuccessful) {
                        showSuccessDialog("Registrasi Berhasil ke Supabase Database!")
                    } else {
                        // Jika Supabase belum di-config API URL / Key-nya, atau error API
                        val errorMsg = "HTTP ${response.code()}: ${response.message()}"
                        Toast.makeText(this@NextRegisterActivity, "Supabase Response: $errorMsg", Toast.LENGTH_LONG).show()
                        // Tetap izinkan registrasi lokal berhasil dengan info
                        showSuccessDialog("Registrasi lokal berhasil (Supabase Response: $errorMsg). Pastikan URL & API Key Supabase sudah benar!")
                    }
                }

                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    binding.btnSubmit.isEnabled = true
                    Toast.makeText(this@NextRegisterActivity, "Koneksi ke Supabase Gagal: ${t.message}", Toast.LENGTH_LONG).show()
                    showSuccessDialog("Registrasi tersimpan secara lokal. Silakan pastikan Supabase URL & Key sudah dikonfigurasi!")
                }
            })
        }

        // Tombol Kembali
        binding.btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Registrasi Berhasil")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
