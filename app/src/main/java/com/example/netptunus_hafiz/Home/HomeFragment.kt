package com.example.netptunus_hafiz.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.netptunus_hafiz.AuthActivity
import com.example.netptunus_hafiz.Home.pertemuan_4.FourthActivity
import com.example.netptunus_hafiz.Home.pertemuan_5.FifthActivity
import com.example.netptunus_hafiz.Home.pertemuan_7.SevenActivity
import com.example.netptunus_hafiz.ProfileActivity
import com.example.netptunus_hafiz.R
import com.example.netptunus_hafiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Home"
        }

        binding.btnToFourth.setOnClickListener {
            val i = Intent(requireContext(), FourthActivity::class.java)

            i.putExtra("name", "Politeknik Caltex Riau")
            i.putExtra("from", "Rumbai")
            i.putExtra("age", 25)
            startActivity(i)
        }

        binding.btnToFifth.setOnClickListener {
            val i = Intent(requireContext(), FifthActivity::class.java)
            startActivity(i)
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        binding.btnProfile.setOnClickListener {
            val i = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(i)
        }

        binding.btnToSeven.setOnClickListener {
            val i = Intent(requireContext(), SevenActivity::class.java)
            startActivity(i)
        }
    }
}