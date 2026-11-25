package com.example.netptunus_hafiz.network

import com.example.netptunus_hafiz.model.NoteModel
import com.example.netptunus_hafiz.model.ProductModel
import com.example.netptunus_hafiz.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface SupabaseApiService {

    // ==========================================
    // USERS API (Register & Login)
    // ==========================================
    @POST("users")
    fun registerUser(@Body user: UserModel): Call<List<UserModel>>

    @GET("users")
    fun getUsers(): Call<List<UserModel>>

    @GET("users")
    fun getUserByUsername(@Query("username") usernameFilter: String): Call<List<UserModel>>

    // ==========================================
    // PRODUCTS API (Custom Topik 1 - CRUD)
    // ==========================================
    @GET("products")
    fun getProducts(@Query("order") order: String = "id.desc"): Call<List<ProductModel>>

    @POST("products")
    fun createProduct(@Body product: ProductModel): Call<List<ProductModel>>

    @PATCH("products")
    fun updateProduct(
        @Query("id") idFilter: String, // Format: eq.123
        @Body product: ProductModel
    ): Call<List<ProductModel>>

    @DELETE("products")
    fun deleteProduct(@Query("id") idFilter: String): Call<Void>

    // ==========================================
    // NOTES API (Custom Topik 2 - CRUD)
    // ==========================================
    @GET("notes")
    fun getNotes(@Query("order") order: String = "id.desc"): Call<List<NoteModel>>

    @POST("notes")
    fun createNote(@Body note: NoteModel): Call<List<NoteModel>>

    @PATCH("notes")
    fun updateNote(
        @Query("id") idFilter: String, // Format: eq.123
        @Body note: NoteModel
    ): Call<List<NoteModel>>

    @DELETE("notes")
    fun deleteNote(@Query("id") idFilter: String): Call<Void>
}
