package com.example.booksearch.network

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("author") val author: String,
    @SerializedName("price") val price: String,
    @SerializedName("publisher") val publisher: String
)