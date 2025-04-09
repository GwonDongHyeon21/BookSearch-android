package com.example.data.network

import com.example.domain.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("v1/search/book.json")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("display") display: Int = 10,
        @Query("start") start: Int
    ): BookResponse
}