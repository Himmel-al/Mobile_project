package com.example.netptunus_hafiz.More

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.netptunus_hafiz.AuthActivity
import com.example.netptunus_hafiz.Home.pertemuan_4.FourthActivity
import com.example.netptunus_hafiz.Home.pertemuan_5.FifthActivity
import com.example.netptunus_hafiz.Home.pertemuan_7.SevenActivity
import com.example.netptunus_hafiz.NoteActivity
import com.example.netptunus_hafiz.ProductActivity
import com.example.netptunus_hafiz.ProfileActivity
import com.example.netptunus_hafiz.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", null)
        if (!savedUsername.isNullOrEmpty()) {
            binding.tvProfileName.text = savedUsername
        }

        binding.btnMenuProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnMenuSecurity.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Security dalam pengembangan", Toast.LENGTH_SHORT).show()
        }

        binding.btnMenuAkunTersambung.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Akun tersambung dalam pengembangan", Toast.LENGTH_SHORT).show()
        }

        binding.btnMenuLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        binding.btnToFourth.setOnClickListener {
            val intent = Intent(requireContext(), FourthActivity::class.java)
            startActivity(intent)
        }

        binding.btnToFifth.setOnClickListener {
            val intent = Intent(requireContext(), FifthActivity::class.java)
            startActivity(intent)
        }

        binding.btnToSeven.setOnClickListener {
            val intent = Intent(requireContext(), SevenActivity::class.java)
            startActivity(intent)
        }

        binding.btnToProductCrud.setOnClickListener {
            val intent = Intent(requireContext(), ProductActivity::class.java)
            startActivity(intent)
        }

        binding.btnToNoteCrud.setOnClickListener {
            val intent = Intent(requireContext(), NoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLogoutConfirmation() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
