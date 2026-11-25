package com.example.netptunus_hafiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netptunus_hafiz.databinding.ItemProductBinding
import com.example.netptunus_hafiz.model.ProductModel
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private var list: List<ProductModel>,
    private val onEditClick: (ProductModel) -> Unit,
    private val onDeleteClick: (ProductModel) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        holder.binding.tvProductTitle.text = item.title
        holder.binding.tvProductDesc.text = item.description
        holder.binding.tvProductPrice.text = formatRupiah.format(item.price)
        holder.binding.tvProductStock.text = "Stok: ${item.stock}"

        holder.binding.btnEditProduct.setOnClickListener { onEditClick(item) }
        holder.binding.btnDeleteProduct.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<ProductModel>) {
        list = newList
        notifyDataSetChanged()
    }
}
