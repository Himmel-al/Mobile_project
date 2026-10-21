package com.example.netptunus_hafiz.Home.pertemuan_3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.netptunus_hafiz.R
import com.example.netptunus_hafiz.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi komponen
//        val inputNama: EditText = findViewById(R.id.inputNama)
//        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, ThirdResultActivity::class.java)
            startActivity(intent)

            //Mengambil value dari inputNama dan menampilkan di Logcat
            val nama = binding.inputNama.text
            Log.e("Klik btnSubmit","Tombol berhasil di tekan. Isi dari inputNama = $nama")

            Toast.makeText(this, "pesan berhasil dikirim ke: $nama", Toast.LENGTH_SHORT).show()
        }
    }
}