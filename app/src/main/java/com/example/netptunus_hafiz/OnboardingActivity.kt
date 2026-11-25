package com.example.netptunus_hafiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.netptunus_hafiz.adapter.OnboardingAdapter
import com.example.netptunus_hafiz.databinding.ActivityOnboardingBinding
import com.example.netptunus_hafiz.domain.OnboardingItem

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private val onboardingItems = ArrayList<OnboardingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupOnboardingItems()
        setupViewPager()
        setupIndicators()
        setCurrentIndicator(0)

        binding.btnNext.setOnClickListener {
            if (binding.viewPagerOnboarding.currentItem + 1 < adapter.itemCount) {
                binding.viewPagerOnboarding.currentItem += 1
            } else {
                navigateToAuth()
            }
        }

        binding.btnSkip.setOnClickListener {
            navigateToAuth()
        }
    }

    private fun setupOnboardingItems() {
        onboardingItems.add(
            OnboardingItem(
                R.drawable.lemari,
                "Selamat Datang di SIPP",
                "Sistem Informasi Pemesanan Perabotan terbaik untuk memenuhi kebutuhan furniture impian rumah Anda."
            )
        )
        onboardingItems.add(
            OnboardingItem(
                R.drawable.sofa,
                "Pilihan Furniture Lengkap",
                "Temukan beragam pilihan lemari, meja, sofa, dan perabotan rumah tangga modern berkualitas tinggi."
            )
        )
        onboardingItems.add(
            OnboardingItem(
                R.drawable.meja,
                "Pemesanan Cepat & Praktis",
                "Pesan perabotan kesukaan Anda dalam beberapa langkah mudah dan siap diantar langsung ke lokasi Anda."
            )
        )
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(onboardingItems)
        binding.viewPagerOnboarding.adapter = adapter
        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (position == adapter.itemCount - 1) {
                    binding.btnNext.text = "Mulai"
                    binding.btnSkip.visibility = View.GONE
                } else {
                    binding.btnNext.text = "Lanjut"
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)

        binding.layoutIndicators.removeAllViews()
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.indicator_inactive
                )
            )
            indicators[i]?.layoutParams = layoutParams
            binding.layoutIndicators.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    private fun navigateToAuth() {
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        sharedPref.edit().putBoolean("isOnboardingCompleted", true).apply()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}
