package com.example.netptunus_hafiz.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    val id: Long? = null,
    
    @SerializedName("nama")
    val nama: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("tgl_lahir")
    val tglLahir: String,
    
    @SerializedName("jenis_kelamin")
    val jenisKelamin: String,
    
    @SerializedName("username")
    val username: String,
    
    @SerializedName("password")
    val password: String,
    
    @SerializedName("created_at")
    val createdAt: String? = null
)
