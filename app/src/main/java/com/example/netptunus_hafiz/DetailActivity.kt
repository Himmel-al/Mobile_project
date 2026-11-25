package com.example.netptunus_hafiz

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.netptunus_hafiz.databinding.ActivityDetailBinding
import com.example.netptunus_hafiz.domain.PopularDomain

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("object", PopularDomain::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("object") as? PopularDomain
        }

        if (item != null) {
            binding.tvHeaderTitle.text = item.title
            binding.tvDetailTitle.text = item.title
            binding.tvDetailPrice.text = "Rp " + item.price.toInt()

            val drawableResourceId = resources.getIdentifier(
                item.picUrl, "drawable", packageName
            )
            if (drawableResourceId != 0) {
                binding.imgProductDetail.setImageResource(drawableResourceId)
            }

            if (!item.description.isNullOrEmpty()) {
                binding.tvDetailDesc.text = item.description
            }
        }

        binding.btnPesan.setOnClickListener {
            Toast.makeText(this, "Pesanan berhasil dibuat!", Toast.LENGTH_SHORT).show()
        }
    }
}
