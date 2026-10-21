package com.example.netptunus_hafiz.Message

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
import com.example.netptunus_hafiz.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Message"
        }



    }
}