package com.example.booksearch.network

import com.example.booksearch.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookApi {
    @Headers(
        "X-Naver-Client-Id: ${BuildConfig.CLIENT_ID}",
        "X-Naver-Client-Secret: ${BuildConfig.CLIENT_SECRET}"
    )
    @GET("v1/search/book.json")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("display") display: Int = 10,
        @Query("start") start: Int
    ): BookResponse
}