package com.example.netptunus_hafiz.Message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.netptunus_hafiz.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHubungi1.setOnClickListener {
            Toast.makeText(requireContext(), "Menghubungi layanan pelanggan BM Perabot...", Toast.LENGTH_SHORT).show()
        }

        binding.btnHubungi2.setOnClickListener {
            Toast.makeText(requireContext(), "Menghubungi layanan pelanggan BM Perabot...", Toast.LENGTH_SHORT).show()
        }

        binding.btnSearchAktivitas.setOnClickListener {
            Toast.makeText(requireContext(), "Cari aktivitas pesanan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
