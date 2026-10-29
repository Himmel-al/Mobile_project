package com.example.netptunus_hafiz.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netptunus_hafiz.R
import com.example.netptunus_hafiz.adapter.PopularAdapter
import com.example.netptunus_hafiz.domain.PopularDomain

class HomeFragment : Fragment() {
    private var recyclerViewPopular: RecyclerView? = null
    private var adapterPopular: RecyclerView.Adapter<*>? = null

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
    }

    private fun initRecyclerView(view: View) {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular)
        recyclerViewPopular?.layoutManager = linearLayoutManager

        val popularList = ArrayList<PopularDomain>()
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
}
