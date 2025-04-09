package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("items") val books: List<Book>
)