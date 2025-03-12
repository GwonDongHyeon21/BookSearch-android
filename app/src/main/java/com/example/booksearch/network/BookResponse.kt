package com.example.booksearch.network

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("items") val books: List<Book>
)