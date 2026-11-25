package com.example.netptunus_hafiz.model

import com.google.gson.annotations.SerializedName

data class NoteModel(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("created_at")
    val createdAt: String? = null
)
