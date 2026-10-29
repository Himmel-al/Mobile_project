package com.example.netptunus_hafiz.Home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netptunus_hafiz.R
import com.example.netptunus_hafiz.adapter.PopularAdapter
import com.example.netptunus_hafiz.domain.PopularDomain

class HomeFragment : Fragment() {
    private var recyclerViewPopular: RecyclerView? = null
    private var adapterPopular: PopularAdapter? = null
    private val popularList = ArrayList<PopularDomain>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
        initSearch(view)
    }

    private fun initRecyclerView(view: View) {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular)
        recyclerViewPopular?.layoutManager = linearLayoutManager

        popularList.clear()
        popularList.add(
            PopularDomain(
                "Lemari Minimum",
                "Lemari pakaian kayu",
                "pop_1",
                15,
                4.5,
                250000.0
            )
        )
        popularList.add(
            PopularDomain(
                "Lemari Modern",
                "Lemari 2 pintu elegan",
                "pop_2",
                20,
                4.8,
                350000.0
            )
        )

        adapterPopular = PopularAdapter(popularList)
        recyclerViewPopular?.adapter = adapterPopular
    }

    private fun initSearch(view: View) {
        val searchEditText = view.findViewById<EditText>(R.id.searchbtn)
        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<PopularDomain>()
        for (item in popularList) {
            val titleMatch = item.title?.contains(text, ignoreCase = true) == true
            val descMatch = item.description?.contains(text, ignoreCase = true) == true
            if (titleMatch || descMatch) {
                filteredList.add(item)
            }
        }
        adapterPopular?.filterList(filteredList)
    }
}
