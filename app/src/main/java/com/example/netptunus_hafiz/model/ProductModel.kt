package com.example.netptunus_hafiz.model

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("created_at")
    val createdAt: String? = null
)
