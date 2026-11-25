package com.example.netptunus_hafiz

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netptunus_hafiz.adapter.ProductAdapter
import com.example.netptunus_hafiz.databinding.ActivityProductBinding
import com.example.netptunus_hafiz.model.ProductModel
import com.example.netptunus_hafiz.network.SupabaseApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()

        binding.fabAddProduct.setOnClickListener {
            showProductDialog(null)
        }

        loadProducts()
    }

    private fun setupToolbar() {
        binding.toolbarProduct.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarProduct.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(
            productList,
            onEditClick = { product -> showProductDialog(product) },
            onDeleteClick = { product -> confirmDeleteProduct(product) }
        )
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = adapter
    }

    private fun loadProducts() {
        binding.progressBarProduct.visibility = View.VISIBLE
        SupabaseApiClient.getService().getProducts().enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                binding.progressBarProduct.visibility = View.GONE
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    productList.clear()
                    productList.addAll(data)
                    adapter.updateData(productList)
                } else {
                    Toast.makeText(
                        this@ProductActivity,
                        "Gagal memuat produk: ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                binding.progressBarProduct.visibility = View.GONE
                Toast.makeText(
                    this@ProductActivity,
                    "Koneksi gagal: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showProductDialog(product: ProductModel?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.viewproduct_list, null)
        val isEdit = product != null

        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle(if (isEdit) "Edit Produk" else "Tambah Produk")

        // Buat input dialog secara programmatic jika layout khusus belum komplit
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val etTitle = EditText(this).apply {
            hint = "Judul / Nama Produk"
            setText(product?.title ?: "")
        }
        val etDesc = EditText(this).apply {
            hint = "Deskripsi Produk"
            setText(product?.description ?: "")
        }
        val etPrice = EditText(this).apply {
            hint = "Harga Produk (cth: 50000)"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            setText(product?.price?.toString() ?: "")
        }
        val etStock = EditText(this).apply {
            hint = "Stok (cth: 10)"
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(product?.stock?.toString() ?: "")
        }

        layout.addView(etTitle)
        layout.addView(etDesc)
        layout.addView(etPrice)
        layout.addView(etStock)

        dialogBuilder.setView(layout)

        dialogBuilder.setPositiveButton("Simpan") { dialog, _ ->
            val title = etTitle.text.toString().trim()
            val desc = etDesc.text.toString().trim()
            val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val stock = etStock.text.toString().toIntOrNull() ?: 0

            if (title.isEmpty()) {
                Toast.makeText(this, "Nama produk wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            if (isEdit && product?.id != null) {
                val updatedProduct = ProductModel(
                    id = product.id,
                    title = title,
                    description = desc,
                    price = price,
                    stock = stock
                )
                updateProductInSupabase(product.id, updatedProduct)
            } else {
                val newProduct = ProductModel(
                    title = title,
                    description = desc,
                    price = price,
                    stock = stock
                )
                createProductInSupabase(newProduct)
            }
            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Batal", null)
        dialogBuilder.show()
    }

    private fun createProductInSupabase(product: ProductModel) {
        binding.progressBarProduct.visibility = View.VISIBLE
        SupabaseApiClient.getService().createProduct(product).enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                binding.progressBarProduct.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@ProductActivity, "Produk berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    loadProducts()
                } else {
                    Toast.makeText(this@ProductActivity, "Gagal tambah: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                binding.progressBarProduct.visibility = View.GONE
                Toast.makeText(this@ProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProductInSupabase(id: Long, product: ProductModel) {
        binding.progressBarProduct.visibility = View.VISIBLE
        SupabaseApiClient.getService().updateProduct("eq.$id", product).enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                binding.progressBarProduct.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@ProductActivity, "Produk berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    loadProducts()
                } else {
                    Toast.makeText(this@ProductActivity, "Gagal update: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                binding.progressBarProduct.visibility = View.GONE
                Toast.makeText(this@ProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmDeleteProduct(product: ProductModel) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Produk")
            .setMessage("Apakah Anda yakin ingin menghapus '${product.title}'?")
            .setPositiveButton("Hapus") { _, _ ->
                product.id?.let { deleteProductInSupabase(it) }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteProductInSupabase(id: Long) {
        binding.progressBarProduct.visibility = View.VISIBLE
        SupabaseApiClient.getService().deleteProduct("eq.$id").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.progressBarProduct.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(this@ProductActivity, "Produk berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    loadProducts()
                } else {
                    Toast.makeText(this@ProductActivity, "Gagal hapus: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.progressBarProduct.visibility = View.GONE
                Toast.makeText(this@ProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
